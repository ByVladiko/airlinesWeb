package com.airlines.controller;

import com.airlines.model.airship.Category;
import com.airlines.model.airship.Flight;
import com.airlines.model.airship.Status;
import com.airlines.model.airship.Ticket;
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

import java.util.Map;
import java.util.UUID;

@Controller
public class TicketController {

    private final TicketRepository ticketRepository;
    private FlightRepository flightRepository;

    @Autowired
    public TicketController(TicketRepository ticketRepository, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping(path = "/tickets")
    public String greeting(Map<String, Object> params) {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        params.put("tickets", tickets);
        params.put("flights", flightRepository.findAll());
        params.put("categories", Category.values());
        params.put("statuses", Status.values());
        return "tickets";
    }

    @PostMapping(path = "/tickets")
    public String add(@RequestParam String flightId,
                      @RequestParam int categoryId,
                      @RequestParam float cost,
                      @RequestParam float baggage,
                      @RequestParam int statusId) {
        Flight flight = flightRepository.findById(UUID.fromString(flightId)).get();
        Ticket ticket = new Ticket(flight, Category.values()[categoryId], cost, baggage, Status.values()[statusId]);
        ticketRepository.save(ticket);

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
                         @RequestParam int statusId) {
        Flight flight = flightRepository.findById(UUID.fromString(flightId)).get();
        Ticket ticket = ticketRepository.findById(UUID.fromString(id)).get();

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
        final Ticket ticket = ticketRepository.findById(UUID.fromString(id)).get();
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }
}
