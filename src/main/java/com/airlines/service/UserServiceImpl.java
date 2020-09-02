package com.airlines.service;

import com.airlines.exception.UserAlreadyExistAuthenticationException;
import com.airlines.exception.UserNotFoundException;
import com.airlines.model.user.Role;
import com.airlines.model.user.User;
import com.airlines.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public User register(User user) throws UserAlreadyExistAuthenticationException {

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistAuthenticationException("User with this name already exists");
        }

        if (user.getUsername().equals("admin")) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        user.setPassword(applicationContext.getAutowireCapableBeanFactory()
                .getBean(BCryptPasswordEncoder.class).encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(UUID id) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("User does not exist");
        }
        return userOptional.get();
    }

    @Override
    public void delete(UUID id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Impossible to delete this user because he does not exist");
        }
        userRepository.deleteById(id);
    }
}
