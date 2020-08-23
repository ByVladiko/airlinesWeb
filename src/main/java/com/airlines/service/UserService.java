package com.airlines.service;

import com.airlines.model.user.User;
import com.airlines.service.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(UUID id) throws UserNotFoundException;

    void delete(UUID id) throws UserNotFoundException;
}
