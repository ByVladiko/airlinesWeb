package com.airlines.controller;

import com.airlines.exception.FlightNotFoundException;
import com.airlines.exception.TicketNotFoundException;
import com.airlines.exception.UserNotFoundException;
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
public class TicketController {

    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository, FlightRepository flightRepository, ClientRepository clientRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.clientRepository = clientRepository;
    }

    @GetMapping(path = "/tickets")
    public String greeting(Map<String, Object> params) {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        params.put("tickets", tickets);
        fillFields(params);
        return "tickets";
    }

    @PostMapping(path = "/tickets")
    public String add(@RequestParam String flightId,
                      @RequestParam int categoryId,
                      @RequestParam float cost,
                      @RequestParam float baggage,
                      @RequestParam int statusId,
                      @RequestParam String clientId,
                      Map<String, Object> model) {
        Client client = null;
        if (!clientId.isEmpty()) {
            try {
                client = clientRepository.findById(UUID.fromString(clientId))
                        .orElseThrow(() -> new UserNotFoundException("User not found"));
            } catch (UserNotFoundException e) {
                model.put("exception", e.getMessage());
                return "clients";
            }
        }
        Flight flight;
        try {
            flight = flightRepository.findById(UUID.fromString(flightId))
                    .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
        } catch (FlightNotFoundException e) {
            model.put("exception", e.getMessage());
            return "redirect:/tickets";
        }

        Ticket ticket = new Ticket(flight, Category.values()[categoryId], cost, baggage, Status.values()[statusId]);
        ticketRepository.save(ticket);

        List<Ticket> ticketList = client.getTickets();
        ticketList.add(ticket);
        client.setTickets(ticketList);

        return "redirect:/tickets";
    }

    @GetMapping(path = "/tickets/delete")
    public String delete(@RequestParam String id) {
        ticketRepository.deleteById(UUID.fromString(id));
        return "redirect:/tickets";
    }

    @PostMapping(path = "/tickets/update")
    public String update(@RequestParam String id,
                         @RequestParam String flightId,
                         @RequestParam int categoryId,
                         @RequestParam float cost,
                         @RequestParam float baggage,
                         @RequestParam int statusId,
                         Map<String, Object> model) {
        Flight flight;
        Ticket ticket;
        try {
            flight = flightRepository.findById(UUID.fromString(flightId))
                    .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
            ticket = ticketRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
        } catch (FlightNotFoundException | TicketNotFoundException e) {
            model.put("exception", e.getMessage());
            return "redirect:/tickets";
        }

        ticket.setFlight(flight);
        ticket.setCategory(Category.values()[categoryId]);
        ticket.setCost(cost);
        ticket.setBaggage(baggage);
        ticket.setStatus(Status.values()[statusId]);

        ticketRepository.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping(value = "/tickets/getById/{id}")
    public ResponseEntity<Ticket> getById(@PathVariable(name = "id") String id) {
        Ticket ticket;
        try {
            ticket = ticketRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping(value = "/tickets/getByClient/{id}")
    public ResponseEntity<List<Ticket>> getByClient(@PathVariable("id") String id, Map<String, Object> model) {
        List<Ticket> ticketList;
        try {
            ticketList = clientRepository.findAllById(UUID.fromString(id));
        } catch (UserNotFoundException e) {
            model.put("exception", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketList, HttpStatus.OK);
    }

    private void fillFields(Map<String, Object> model) {
        model.put("flights", flightRepository.findAll());
        model.put("categories", Category.values());
        model.put("statuses", Status.values());
    }
}
