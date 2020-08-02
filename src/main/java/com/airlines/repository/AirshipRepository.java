package com.airlines.repository;

import com.airlines.model.Airship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirshipRepository extends CrudRepository<Airship, Integer> {

}
