package com.airlines.controller;

import com.airlines.exception.AirshipNotFoundException;
import com.airlines.model.airship.Airship;
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

    private final AirshipRepository airshipRepository;

    @Autowired
    public AirshipController(AirshipRepository airshipRepository) {
        this.airshipRepository = airshipRepository;
    }

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
    public String delete(@RequestParam String id, Map<String, Object> param) {
        if (!airshipRepository.existsById(UUID.fromString(id))) {
            param.put("exception", "Airship not found");
            return "redirect:/airships";
        }
        airshipRepository.deleteById(UUID.fromString(id));
        return "redirect:/airships";
    }

    @PostMapping(path = "/airships/update")
    public String update(@RequestParam String id,
                         @RequestParam String model,
                         @RequestParam int economy,
                         @RequestParam int business,
                         @RequestParam int premium,
                         Map<String, Object> param) {
        Airship airship = null;
        try {
            airship = airshipRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new AirshipNotFoundException("Airship not found"));
        } catch (AirshipNotFoundException e) {
            param.put("exception", e.getMessage());
            return "redirect:/airships";
        }
        airshipRepository.save(airship);
        return "redirect:/airships";
    }

    @GetMapping(value = "/airships/getById/{id}")
    public ResponseEntity<Airship> getById(@PathVariable(name = "id") String id) {
        Airship airship;
        try {
            airship = airshipRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new AirshipNotFoundException("Airship not found"));
        } catch (AirshipNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(airship, HttpStatus.OK);
    }

}
