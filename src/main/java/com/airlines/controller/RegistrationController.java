package com.airlines.controller;

import com.airlines.exception.UserAlreadyExistAuthenticationException;
import com.airlines.model.user.User;
import com.airlines.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
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
