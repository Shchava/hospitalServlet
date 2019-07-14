package ua.training.servlet.hospital.entity;

import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String surname;
    private String email;
    private String passwordHash;
    private Roles role;


    public User() {
    }

    public User(String name, String surname, String email, String passwordHash, Roles role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(long id, String name, String surname, String email, String passwordHash, Roles role) {
        this(name, surname, email, passwordHash, role);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Roles getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}


