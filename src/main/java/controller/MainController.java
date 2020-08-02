package controller;

import model.Airship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import repository.AirshipRepository;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private AirshipRepository airshipRepository;

    @GetMapping
    public String main(Map<String, Object> params) {
        Iterable<Airship> airships = airshipRepository.findAll();
        params.put("airships", airships);
        return "index";
    }

    @PostMapping
    public String add(@RequestParam String model,
                      @RequestParam int economy,
                      @RequestParam int business,
                      @RequestParam int premium,
                      Map<String, Object> params) {

        Airship airship = new Airship(model, economy, business, premium);
        airshipRepository.save(airship);

        Iterable<Airship> airships = airshipRepository.findAll();
        params.put("airships", airships);

        return "index";
    }

}
