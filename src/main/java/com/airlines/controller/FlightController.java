package com.airlines.controller;

import com.airlines.model.Airship;
import com.airlines.model.Flight;
import com.airlines.model.Route;
import com.airlines.repository.AirshipRepository;
import com.airlines.repository.FlightRepository;
import com.airlines.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Controller
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private AirshipRepository airshipRepository;

    @GetMapping(path = "/flights")
    public String greeting(Map<String, Object> params) {
        Iterable<Flight> flights = flightRepository.findAll();
        Iterable<Route> routes = routeRepository.findAll();
        Iterable<Airship> airships = airshipRepository.findAll();
        params.put("flights", flights);
        params.put("routes", routes);
        params.put("airships", airships);
        return "flights";
    }

    @PostMapping(path = "/flights")
    public String add(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date dateDeparture,
                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date dateArrival,
                      @RequestParam String routeId,
                      @RequestParam String airshipId,
                      Map<String, Object> params) {
        Airship airship = airshipRepository.findById(UUID.fromString(airshipId)).get();
        Route route = routeRepository.findById(UUID.fromString(routeId)).get();
        Flight flight = new Flight(dateDeparture, dateArrival, airship, route);
        flightRepository.save(flight);

        Iterable<Flight> flights = flightRepository.findAll();
        params.put("flights", flights);

        return "flights";
    }

}
