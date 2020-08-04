package com.airlines.repository;

import com.airlines.model.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FlightRepository extends CrudRepository<Flight, UUID> {

    @Override
    Optional<Flight> findById(UUID uuid);
}
