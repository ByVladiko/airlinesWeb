package com.airlines.controller;

import com.airlines.model.Category;
import com.airlines.model.Flight;
import com.airlines.model.Status;
import com.airlines.model.Ticket;
import com.airlines.repository.FlightRepository;
import com.airlines.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private FlightRepository flightRepository;

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
                      @RequestParam Category category,
                      @RequestParam float cost,
                      @RequestParam float baggage,
                      @RequestParam Status status,
                      Map<String, Object> params) {

        Flight flight = flightRepository.findById(UUID.fromString(flightId)).get();
        Ticket ticket = new Ticket(flight, category, cost, baggage, status);
        ticketRepository.save(ticket);

        Iterable<Ticket> tickets = ticketRepository.findAll();
        params.put("tickets", tickets);

        return "tickets";
    }
}
