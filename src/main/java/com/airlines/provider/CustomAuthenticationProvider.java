package com.airlines.provider;

import com.airlines.model.user.Role;
import com.airlines.model.user.User;
import com.airlines.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public CustomAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        BCryptPasswordEncoder passwordEncoder = applicationContext.getAutowireCapableBeanFactory().getBean(BCryptPasswordEncoder.class);

        User myUser = userService.findByUsername(username);
        if (myUser == null) {
            throw new BadCredentialsException("Unknown user " + username);
        }
        if (!passwordEncoder.matches(password, myUser.getPassword())) {
            throw new BadCredentialsException("Bad password");
        }
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getUsername())
                .password(myUser.getPassword())
                .roles(Arrays.toString(Role.values()))
                .build();
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
