package ua.training.servlet.hospital.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.atomic.AtomicBoolean;

public class AuthServiceImpl implements AuthService {
    private static final Logger logger = LogManager.getLogger(AuthServiceImpl.class);

    UserService userService;
    PasswordEncoder encoder;

    public AuthServiceImpl(UserService service) {
        userService = service;
        encoder = PasswordEncoder.getInstance();
    }

    @Override
    public boolean checkAuthority(String email, String password) {
        logger.debug("checking authority with email " + email);
        AtomicBoolean matches = new AtomicBoolean(false);
        userService.getUser(email).ifPresent(user -> {
            matches.set(encoder.checkPassword(password, user.getPasswordHash()));
        });
        return matches.get();
    }
}
