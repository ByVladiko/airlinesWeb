package com.airlines.service.user;

import com.airlines.exception.UserNotFoundException;
import com.airlines.model.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(UUID id) throws UserNotFoundException;

    void delete(UUID id) throws UserNotFoundException;
}
