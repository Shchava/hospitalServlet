package ua.training.servlet.hospital.service.user;

import ua.training.servlet.hospital.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUser(String email);
}
