package com.airlines.repository;

import com.airlines.model.Flight;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends CrudRepository<Flight, UUID> {

    @Override
    Optional<Flight> findById(UUID uuid);
}
