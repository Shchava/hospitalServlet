package ua.training.servlet.hospital.entity;

import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.Objects;

public class User {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String passwordHash;
    private String info;
    private Roles role;


    public User() {
    }

    public User(long id) {
        this.id = id;
    }

    public User(String name, String surname, String patronymic, String email, String passwordHash, Roles role) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public User(String name, String surname, String patronymic, String email, String passwordHash, String info, Roles role) {
        this(name, surname, patronymic,email, passwordHash, role);
        this.info = info;
    }

    public User(long id, String name, String surname, String patronymic, String email, String passwordHash, Roles role) {
        this(name, surname, patronymic,email, passwordHash, role);
        this.id = id;
    }

    public User(long id, String name, String surname, String patronymic, String email, String passwordHash, String info, Roles role) {
        this(name, surname, patronymic,email, passwordHash, info ,role);
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

    public String getPatronymic() {
        return patronymic;
    }

    public String getInfo() {
        return info;
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

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setInfo(String info) {
        this.info = info;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(patronymic, user.patronymic) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, email, passwordHash, role);
    }
}


