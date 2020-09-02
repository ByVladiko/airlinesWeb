package com.airlines.controller;

import com.airlines.exception.ClientNotFoundException;
import com.airlines.exception.FlightNotFoundException;
import com.airlines.exception.TicketNotFoundException;
import com.airlines.model.airship.*;
import com.airlines.repository.ClientRepository;
import com.airlines.repository.FlightRepository;
import com.airlines.repository.TicketRepository;
import com.airlines.service.PurchaseTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class PurchaseController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    PurchaseTicketService purchaseTicketService;

    @GetMapping(value = "/clients/{id}/purchase")
    public String purchaseForClient(@PathVariable(name = "id") String id, Map<String, Object> params) {
        Client client;
        try {
            client = clientRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        } catch (ClientNotFoundException e) {
            params.put("message", e.getMessage());
            return "redirect:/clients";
        }
        List<Ticket> ticketList = ticketRepository.findTicketsByStatus(Status.FREE);

        params.put("tickets", ticketList);
        params.put("client", client);
        fillFields(params);

        return "purchase";
    }

    @PostMapping(path = "/clients/{id}/purchase")
    public String filter(@PathVariable(name = "id") String id,
                         @RequestParam(required = false) String flightId,
                         @RequestParam(required = false) Integer category,
                         Map<String, Object> params) {
        Flight flight;
        Client client;
        try {
            client = clientRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));
            flight = flightRepository.findById(UUID.fromString(flightId))
                    .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
        } catch (FlightNotFoundException | ClientNotFoundException e) {
            params.put("message", e.getMessage());
            return "purchase";
        }

        List<Ticket> ticketList = ticketRepository.findTicketsByStatusAndFlightAndCategory(Status.FREE,
                flight, Category.values()[category]);
        params.put("tickets", ticketList);
        params.put("client", client);
        fillFields(params);
        return "purchase";
    }

    @PostMapping(value = "/clients/{idClient}/purchase/preview")
    public @ResponseBody
    ResponseEntity<Map<String, Object>> getInfoAboutPurchase(@RequestParam String idTicket,
                                                             @PathVariable String idClient) {
        Ticket ticket;
        Client client;
        try {
            ticket = ticketRepository.findById(UUID.fromString(idTicket))
                    .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
            client = clientRepository.findById(UUID.fromString(idClient))
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        } catch (TicketNotFoundException | ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> map = new HashMap<>();

        if (client.getBill() < ticket.getCost()) {
            map.put("warning", "Not enough money on account");
            return new ResponseEntity<>(map, HttpStatus.I_AM_A_TEAPOT);
        }

        map.put("ticket", ticket);
        map.put("client", client);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/clients/{idClient}/purchase/confirm")
    public String buyingTicket(@RequestParam String idTicket,
                               @PathVariable String idClient,
                               Map<String, Object> params) {
        Ticket ticket;
        Client client;
        try {
            ticket = ticketRepository.findById(UUID.fromString(idTicket))
                    .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
            client = clientRepository.findById(UUID.fromString(idClient))
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        } catch (TicketNotFoundException | ClientNotFoundException e) {
            params.put("warning", e.getMessage());
            return "redirect:/clients/" + idClient + "/purchase";
        }

        purchaseTicketService.purchase(client, ticket);
        String fio = String.format("%s %s %s", client.getFirstName(), client.getMiddleName(), client.getLastName());
        params.put("success", "Ticket has been added to account of client: " + fio);
        return "redirect:/clients";
    }

    private void fillFields(Map<String, Object> model) {
        model.put("flights", flightRepository.findAll());
        model.put("categories", Category.values());
        model.put("statuses", Status.values());
    }

}
