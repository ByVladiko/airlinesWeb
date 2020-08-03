package com.airlines.repository;

import com.airlines.model.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    @Override
    Optional<Ticket> findById(UUID uuid);
}
