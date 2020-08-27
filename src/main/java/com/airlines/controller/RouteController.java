package com.airlines.controller;

import com.airlines.exception.RouteNotFoundException;
import com.airlines.model.airship.Route;
import com.airlines.repository.RouteRepository;
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
public class RouteController {

    private final RouteRepository routeRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @GetMapping(path = "/routes")
    public String greeting(Map<String, Object> params) {
        Iterable<Route> routes = routeRepository.findAll();
        params.put("routes", routes);
        return "routes";
    }

    @PostMapping(path = "/routes")
    public String add(@RequestParam String startPoint,
                      @RequestParam String endPoint) {
        Route route = new Route(startPoint, endPoint);
        routeRepository.save(route);

        return "redirect:/routes";
    }

    @GetMapping(path = "/routes/delete")
    public String delete(@RequestParam String id) {
        routeRepository.deleteById(UUID.fromString(id));
        return "redirect:/routes";
    }

    @PostMapping(path = "/routes/update")
    public String update(@RequestParam String id,
                         @RequestParam String startPoint,
                         @RequestParam String endPoint,
                         Map<String, Object> model) {
        Route route;
        try {
            route = routeRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new RouteNotFoundException("Route not found"));
        } catch (RouteNotFoundException e) {
            model.put("exception", e.getMessage());
            return "redirect:/routes";
        }
        route.setStartPoint(startPoint);
        route.setEndPoint(endPoint);
        routeRepository.save(route);
        return "redirect:/routes";
    }

    @GetMapping(value = "/routes/getById/{id}")
    public ResponseEntity<Route> getById(@PathVariable(name = "id") String id) {
        Route route = null;
        try {
            route = routeRepository.findById(UUID.fromString(id))
                    .orElseThrow(() -> new RouteNotFoundException("Route not found"));
        } catch (RouteNotFoundException e) {
            return new ResponseEntity<>(route, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(route, HttpStatus.OK);
    }

}
