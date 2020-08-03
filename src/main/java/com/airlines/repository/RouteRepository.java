package com.airlines.repository;

import com.airlines.model.Route;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface RouteRepository extends CrudRepository<Route, UUID> {

    @Override
    Optional<Route> findById(UUID uuid);
}
