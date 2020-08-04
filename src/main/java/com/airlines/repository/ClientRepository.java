package com.airlines.repository;

import com.airlines.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends CrudRepository<Client, UUID> {

    @Override
    Optional<Client> findById(UUID uuid);
}
