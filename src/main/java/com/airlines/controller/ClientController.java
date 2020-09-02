package com.airlines.controller;

import com.airlines.exception.ClientNotFoundException;
import com.airlines.exception.FlightNotFoundException;
import com.airlines.model.airship.*;
import com.airlines.repository.ClientRepository;
import com.airlines.repository.FlightRepository;
import com.airlines.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ClientController {

    private final ClientRepository clientRepository;
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository, TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.clientRepository = clientRepository;
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping(path = "/clients")
    public String greeting(Map<String, Object> params) {
        Iterable<Client> clients = clientRepository.findAll();
        params.put("clients", clients);
        return "clients";
    }

    @PostMapping(path = "/clients")
    public String add(@RequestParam String firstName,
                      @RequestParam String middleName,
                      @RequestParam String lastName) {

        Client client = new Client(firstName, middleName, lastName);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping(path = "/clients/delete")
    public String delete(@RequestParam String id) {
        clientRepository.deleteById(UUID.fromString(id));
        return "redirect:/clients";
    }

    @PostMapping(path = "/clients/update")
    public String update(@RequestParam String id,
                         @RequestParam String firstName,
                         @RequestParam String middleName,
                         @RequestParam String lastName,
                         @RequestParam float bill) {
        Client client = clientRepository.findById(UUID.fromString(id)).get();

        client.setFirstName(firstName);
        client.setMiddleName(middleName);
        client.setLastName(lastName);
        client.setBill(bill);

        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping(value = "/clients/getById/{id}")
    public ResponseEntity<Client> getById(@PathVariable(name = "id") String id) {
        final Client client = clientRepository.findById(UUID.fromString(id)).get();
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

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

    private void fillFields(Map<String, Object> model) {
        model.put("flights", flightRepository.findAll());
        model.put("categories", Category.values());
        model.put("statuses", Status.values());
    }

}
