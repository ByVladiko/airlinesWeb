package com.airlines.controller;

import com.airlines.model.Route;
import com.airlines.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping(path = "/routes")
    public String greeting(Map<String, Object> params) {
        Iterable<Route> routes = routeRepository.findAll();
        params.put("routes", routes);
        return "routes";
    }

    @PostMapping(path = "/routes")
    public String add(@RequestParam String startPoint,
                      @RequestParam String endPoint,
                      Map<String, Object> params) {
        Route route = new Route(startPoint, endPoint);
        routeRepository.save(route);

        Iterable<Route> routes = routeRepository.findAll();
        params.put("routes", routes);

        return "routes";
    }

}
