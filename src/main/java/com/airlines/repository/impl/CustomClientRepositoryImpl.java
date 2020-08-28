package com.airlines.repository.impl;

import com.airlines.exception.UserNotFoundException;
import com.airlines.model.airship.Client;
import com.airlines.model.airship.Ticket;
import com.airlines.repository.ClientRepository;
import com.airlines.repository.CustomClientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class CustomClientRepositoryImpl implements CustomClientRepository {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Ticket> findAllById(UUID id) throws UserNotFoundException {
        Client client = clientRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return client.getTickets();
    }

    @Override
    public List<Ticket> findAllAvailable() {
        return null;
    }
}
