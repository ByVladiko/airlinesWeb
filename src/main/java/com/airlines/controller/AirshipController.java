package com.airlines.controller;

import com.airlines.model.Airship;
import com.airlines.repository.AirshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.UUID;

@Controller
public class AirshipController {

    @Autowired
    private AirshipRepository airshipRepository;

    @GetMapping(path = "/airships")
    public String greeting(Map<String, Object> params) {
        Iterable<Airship> airships = airshipRepository.findAll();
        params.put("airships", airships);
        return "airships";
    }

    @PostMapping(path = "/airships")
    public String add(@RequestParam String model,
                      @RequestParam int economy,
                      @RequestParam int business,
                      @RequestParam int premium) {
        Airship airship = new Airship(model, economy, business, premium);
        airshipRepository.save(airship);
        return "redirect:/airships";
    }

    @GetMapping(path = "/airships/delete")
    public String delete(@RequestParam String id) {
        airshipRepository.deleteById(UUID.fromString(id));
        return "redirect:/airships";
    }

    @PostMapping(path = "/airships/update")
    public String update(@RequestParam String id,
                         @RequestParam String model,
                         @RequestParam int economy,
                         @RequestParam int business,
                         @RequestParam int premium) {
        Airship airship = new Airship(UUID.fromString(id), model, economy, business, premium);
        airshipRepository.save(airship);
        return "redirect:/airships";
    }

    @GetMapping(value = "/airships/getById/{id}")
    public ResponseEntity<Airship> getById(@PathVariable(name = "id") String id) {
        final Airship airship = airshipRepository.findById(UUID.fromString(id)).get();
        return new ResponseEntity<>(airship, HttpStatus.OK);
    }

}
