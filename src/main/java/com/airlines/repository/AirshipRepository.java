package com.airlines.repository;

import com.airlines.model.airship.Airship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Repository
@RestController
public interface AirshipRepository extends CrudRepository<Airship, UUID> {

    @Override
    @GetMapping(path = "airships/findById")
    Optional<Airship> findById(@RequestParam UUID id);
}
