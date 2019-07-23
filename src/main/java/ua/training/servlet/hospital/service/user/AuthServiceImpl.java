package ua.training.servlet.hospital.service.user;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthServiceImpl implements AuthService {
    UserService userService;

    public AuthServiceImpl(UserService service) {
        userService = service;
    }

    @Override
    public boolean checkAuthority(String email, String password) {
        AtomicBoolean matches = new AtomicBoolean(false);
        userService.getUser(email).ifPresent(user -> {
            matches.set(BCrypt.checkpw(password, user.getPasswordHash()));
        });
        return matches.get();
    }
}
