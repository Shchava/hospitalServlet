package ua.training.servlet.hospital.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class AuthServiceImplTest {
    @Mock
    UserService userService;
    @InjectMocks
    AuthService authService = new AuthServiceImpl(userService);

    int logRounds = 11;

    String testEmail = "TestAuthServiceEmail@example.com";
    String wrongEmail = "wrongAuthEmail@example.com";
    String testPassword = "testAuthPassword";

    User testUser = new User(
            "TestAuthServiceName",
            "TestAuthServiceSurname",
            "TestAuthServicePatronymic",
            testEmail,
            "",
            Roles.NURSE);

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        String passwordHash = BCrypt.hashpw(testPassword, BCrypt.gensalt(logRounds));
        testUser.setPasswordHash(passwordHash);

        given(userService.getUser(testEmail)).willReturn(Optional.of(testUser));
        given(userService.getUser(wrongEmail)).willReturn(Optional.empty());
    }

    @Test
    public void checkAuthority() {
        assertTrue(authService.checkAuthority(testEmail,testPassword));
        assertFalse(authService.checkAuthority(wrongEmail,testPassword));
        assertFalse(authService.checkAuthority(testEmail,"someWrongPassword"));
    }

    @Test
    public void testBcryptCompatibility(){
        assertTrue(BCrypt.checkpw("123","$2a$04$IRvx7NctpODchwBIPWys1.Tg4g9j1gBpE0.9mTNs6I656BgF/5xOm"));
    }
}