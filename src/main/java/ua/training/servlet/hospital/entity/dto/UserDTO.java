package ua.training.servlet.hospital.entity.dto;


import ua.training.servlet.hospital.entity.enums.Roles;

public class UserDTO {
    private String name;

    private String surname;

    private String patronymic;

    private String email;

    private String password;

    private String confirmPassword;

    private Roles role;
}
