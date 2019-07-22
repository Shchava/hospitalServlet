package ua.training.servlet.hospital.service.user;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserDao repository;

    public UserServiceImpl(UserDao repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> getUser(String email) {
        return repository.findByEmail(email);
    }
}
