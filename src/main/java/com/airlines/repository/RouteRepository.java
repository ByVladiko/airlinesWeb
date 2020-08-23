package com.airlines.repository;

import com.airlines.model.airship.Route;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RouteRepository extends CrudRepository<Route, UUID> {

    @Override
    Optional<Route> findById(UUID uuid);
}
