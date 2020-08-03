package com.airlines.controller;

import com.airlines.model.Airship;
import com.airlines.repository.AirshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
                      @RequestParam int premium,
                      Map<String, Object> params) {
        Airship airship = new Airship(model, economy, business, premium);
        airshipRepository.save(airship);

        Iterable<Airship> airships = airshipRepository.findAll();
        params.put("airships", airships);

        return "airships";
    }

}
