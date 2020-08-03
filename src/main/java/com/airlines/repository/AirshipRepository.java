package com.airlines.repository;

import com.airlines.model.Airship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirshipRepository extends CrudRepository<Airship, UUID> {

    @Override
    Optional<Airship> findById(UUID uuid);
}
