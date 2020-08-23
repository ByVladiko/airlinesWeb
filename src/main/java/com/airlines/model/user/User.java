package com.airlines.model.user;

import javax.persistence.*;
import java.util.UUID;

@Entity()
@Table(name = "usr")
public class User {

    @Id
    private final UUID id;
    private String username;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String username, String password, Role role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(UUID id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
