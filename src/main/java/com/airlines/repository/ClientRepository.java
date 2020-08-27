package com.airlines.repository;

import com.airlines.exception.UserNotFoundException;
import com.airlines.model.airship.Client;
import com.airlines.model.airship.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID>, CustomClientRepository {

    @Override
    Optional<Client> findById(UUID uuid);

    List<Ticket> findAllById(UUID id) throws UserNotFoundException;
}
