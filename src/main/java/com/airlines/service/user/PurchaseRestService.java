package com.airlines.service.user;

import com.airlines.exception.ClientNotFoundException;
import com.airlines.exception.TicketNotFoundException;
import com.airlines.model.airship.Client;
import com.airlines.model.airship.Ticket;
import com.airlines.repository.ClientRepository;
import com.airlines.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Service
public class PurchaseRestService {

    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "/purchase")
    public @ResponseBody
    ResponseEntity<Map<String, Object>> getInfoAboutPurchase(@RequestParam String idTicket, @RequestParam String idClient) {
        Ticket ticket;
        Client client;
        try {
            ticket = ticketRepository.findById(UUID.fromString(idTicket))
                    .orElseThrow(() -> new TicketNotFoundException("User not found"));
            client = clientRepository.findById(UUID.fromString(idClient))
                    .orElseThrow(() -> new ClientNotFoundException("Client not found"));
        } catch (TicketNotFoundException | ClientNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("ticket", ticket);
        map.put("client", client);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
