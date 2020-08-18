package com.airlines.controller;

import com.airlines.model.Client;
import com.airlines.repository.ClientRepository;
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
                      @RequestParam String lastName) {

        Client client = new Client(firstName, middleName, lastName);
        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping(path = "/clients/delete")
    public String delete(@RequestParam String id) {
        clientRepository.deleteById(UUID.fromString(id));
        return "redirect:/clients";
    }

    @PostMapping(path = "/clients/update")
    public String update(@RequestParam String id,
                         @RequestParam String firstName,
                         @RequestParam String middleName,
                         @RequestParam String lastName) {
        Client client = clientRepository.findById(UUID.fromString(id)).get();

        client.setFirstName(firstName);
        client.setMiddleName(middleName);
        client.setLastName(lastName);

        clientRepository.save(client);
        return "redirect:/clients";
    }

    @GetMapping(value = "/clients/getById/{id}")
    public ResponseEntity<Client> getById(@PathVariable(name = "id") String id) {
        final Client client = clientRepository.findById(UUID.fromString(id)).get();
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

}
