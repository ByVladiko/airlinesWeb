package com.airlines.controller;

import com.airlines.model.airship.Ticket;
import com.airlines.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class BuyingTicket {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path = "/purchase/client{id}")
    public String greeting(@PathVariable("id") String id, Map<String, Object> params) {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        params.put("tickets", tickets);
//        fillFields(params);
        return "tickets";
    }

}
