package com.airlines.controller;

import com.airlines.model.airship.Airship;
import com.airlines.model.airship.Flight;
import com.airlines.model.airship.Route;
import com.airlines.repository.AirshipRepository;
import com.airlines.repository.FlightRepository;
import com.airlines.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Controller
public class FlightController {

    private final FlightRepository flightRepository;
    private final RouteRepository routeRepository;
    private final AirshipRepository airshipRepository;

    @Autowired
    public FlightController(FlightRepository flightRepository, RouteRepository routeRepository, AirshipRepository airshipRepository) {
        this.flightRepository = flightRepository;
        this.routeRepository = routeRepository;
        this.airshipRepository = airshipRepository;
    }

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
    public String add(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateDeparture,
                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateArrival,
                      @RequestParam String routeId,
                      @RequestParam String airshipId) {
        Airship airship = airshipRepository.findById(UUID.fromString(airshipId)).get();
        Route route = routeRepository.findById(UUID.fromString(routeId)).get();
        Flight flight = new Flight(dateDeparture, dateArrival, airship, route);
        flightRepository.save(flight);
        return "redirect:/flights";
    }

    @GetMapping(path = "/flights/delete")
    public String delete(@RequestParam String id) {
        flightRepository.deleteById(UUID.fromString(id));
        return "redirect:/flights";
    }

    @PostMapping(path = "/flights/update")
    public String update(@RequestParam String id,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateOfDeparture,
                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateOfArrival,
                         @RequestParam String routeId,
                         @RequestParam String airshipId) {
        Airship airship = airshipRepository.findById(UUID.fromString(airshipId)).get();
        Route route = routeRepository.findById(UUID.fromString(routeId)).get();

        Flight flight = flightRepository.findById(UUID.fromString(id)).get();
        flight.setDateOfDeparture(dateOfDeparture);
        flight.setDateOfArrival(dateOfArrival);
        flight.setAirship(airship);
        flight.setRoute(route);

        flightRepository.save(flight);
        return "redirect:/flights";
    }

    @GetMapping(value = "/flights/getById/{id}")
    public ResponseEntity<Flight> getById(@PathVariable(name = "id") String id) {
        final Flight flight = flightRepository.findById(UUID.fromString(id)).get();
        return new ResponseEntity<>(flight, HttpStatus.OK);
    }

}
