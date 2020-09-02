package com.airlines.service;

import com.airlines.model.airship.Client;
import com.airlines.model.airship.Ticket;

public interface PurchaseTicketService {

    void purchase(Client client, Ticket ticket);

}
