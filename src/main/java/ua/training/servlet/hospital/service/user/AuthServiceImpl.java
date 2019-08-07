package ua.training.servlet.hospital.service.user;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthServiceImpl implements AuthService {
    UserService userService;
    PasswordEncoder encoder;

    public AuthServiceImpl(UserService service) {
        userService = service;
        encoder = PasswordEncoder.getInstance();
    }

    @Override
    public boolean checkAuthority(String email, String password) {
        AtomicBoolean matches = new AtomicBoolean(false);
        userService.getUser(email).ifPresent(user -> {
            matches.set(encoder.checkPassword(password, user.getPasswordHash()));
        });
        return matches.get();
    }
}
