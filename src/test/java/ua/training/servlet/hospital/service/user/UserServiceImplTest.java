package ua.training.servlet.hospital.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {
    @Mock
    UserDao userDao;

    @InjectMocks
    UserServiceImpl service = new UserServiceImpl(userDao);

    String testEmail = "TestUserServiceEmail@example.com";
    String wrongEmail = "wrongEmail@example.com";

    User testUser = new User(
            "TestUserServiceName",
            "TestUserServiceSurname",
            "TestUserServicePatronymic",
            testEmail,
            "password",
            Roles.NURSE);

    @Before
    public void setUp() throws Exception {
        initMocks(this);

        given(userDao.findByEmail(testEmail) ).willReturn(Optional.of(testUser));
        given(userDao.findByEmail(wrongEmail) ).willReturn(Optional.empty());
    }

    @Test
    public void testGetUserWithString(){
        assertEquals(testUser, service.getUser(testEmail).orElse(null));
        assertFalse(service.getUser(wrongEmail).isPresent());
    }
}