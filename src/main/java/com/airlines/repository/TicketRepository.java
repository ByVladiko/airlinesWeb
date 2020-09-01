package com.airlines.repository;

import com.airlines.model.airship.Category;
import com.airlines.model.airship.Flight;
import com.airlines.model.airship.Status;
import com.airlines.model.airship.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, UUID> {

    @Override
    Optional<Ticket> findById(UUID uuid);

    List<Ticket> findTicketsByStatus(Status status);

    List<Ticket> findTicketsByStatusAndFlightAndCategory(Status status, @Nullable Flight flight, @Nullable Category category);
}
