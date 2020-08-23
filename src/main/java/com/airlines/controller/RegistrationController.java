package com.airlines.controller;

import com.airlines.model.user.User;
import com.airlines.repository.UserRepository;
import com.airlines.service.UserService;
import com.airlines.service.exception.UserAlreadyExistAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public RegistrationController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        try {
            userService.register(user);
        } catch (UserAlreadyExistAuthenticationException e) {
            model.put("message", e.getMessage());
            return "registration";
        }
        model.put("message", "Registration has been successful");
        return "redirect:/login";
    }
}
