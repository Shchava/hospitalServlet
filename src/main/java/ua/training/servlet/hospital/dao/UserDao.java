package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.entity.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User>{
    Optional<User> findByEmail(String email);
}
