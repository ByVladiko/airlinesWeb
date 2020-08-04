package com.airlines.controller;

import com.airlines.model.Client;
import com.airlines.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(path = "/clients")
    public String greeting(Map<String, Object> params) {
        Iterable<Client> clients = clientRepository.findAll();
        params.put("clients", clients);
        return "clients";
    }

    @PostMapping(path = "/clients")
    public String add(@RequestParam String firstName,
                      @RequestParam String middleName,
                      @RequestParam String lastName,
                      Map<String, Object> params) {

        Client client = new Client(firstName, middleName, lastName);
        clientRepository.save(client);

        Iterable<Client> clients = clientRepository.findAll();
        params.put("clients", clients);

        return "clients";
    }

}
