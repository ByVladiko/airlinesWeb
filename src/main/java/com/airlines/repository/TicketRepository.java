package com.airlines.repository;

import com.airlines.model.airship.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    @Override
    Optional<Ticket> findById(UUID uuid);
}
