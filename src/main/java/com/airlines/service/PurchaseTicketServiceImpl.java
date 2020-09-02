package com.airlines.service;

import com.airlines.model.airship.Client;
import com.airlines.model.airship.Status;
import com.airlines.model.airship.Ticket;
import com.airlines.repository.ClientRepository;
import com.airlines.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PurchaseTicketServiceImpl implements PurchaseTicketService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Override
    @Transactional
    public void purchase(Client client, Ticket ticket) {
        ticket.setStatus(Status.RESERVED);
        ticketRepository.save(ticket);

        List<Ticket> ticketList = client.getTickets();
        ticketList.add(ticket);

        client.setTickets(ticketList);
        clientRepository.save(client);

        client.setBill(client.getBill() - ticket.getCost());

        ticket.setStatus(Status.SOLD);
        ticketRepository.save(ticket);
    }
}
