package com.airlines.repository;

import com.airlines.exception.UserNotFoundException;
import com.airlines.model.airship.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomClientRepository {

    List<Ticket> findAllById(UUID id) throws UserNotFoundException;

}
