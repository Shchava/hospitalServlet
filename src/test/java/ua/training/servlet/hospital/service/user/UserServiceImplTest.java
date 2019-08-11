package ua.training.servlet.hospital.service.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.entity.dto.UserDTO;
import ua.training.servlet.hospital.entity.enums.Roles;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {
    @Mock
    DaoFactory factory;

    @Mock
    UserDao userDao;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserServiceImpl service = new UserServiceImpl(factory);

    @Mock
    List<ShowUserToDoctorDTO> patientsDtoList;

    String testEmail = "TestUserServiceEmail@example.com";
    String notExistEmail = "test2@email.com";
    String wrongEmail = "wrongEmail@example.com";

    @Captor
    ArgumentCaptor<User> userCaptor;

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

        given(factory.createUserDao()).willReturn(userDao);

        given(userDao.findByEmail(testEmail) ).willReturn(Optional.of(testUser));
        given(userDao.findByEmail(wrongEmail) ).willReturn(Optional.empty());
        given(userDao.findByEmail(notExistEmail) ).willReturn(Optional.empty());
        given(userDao.create(any())).willReturn(true);

        given(encoder.encode(any())).willReturn("cryptedPassword");
    }

    @Test
    public void testGetUserWithString(){
        assertEquals(testUser, service.getUser(testEmail).orElse(null));
        assertFalse(service.getUser(wrongEmail).isPresent());
    }

    @Test
    public void testGetUserById(){
        given(userDao.findById(5)).willReturn(Optional.of(testUser));
        given(userDao.findById(11)).willReturn(Optional.empty());
        assertEquals(testUser,service.getUser(5).get());
        assertFalse(service.getUser(11).isPresent());

    }

    @Test
    public void testRegisterUser() {
        UserDTO dto = new UserDTO();
        dto.setName(testUser.getName());
        dto.setSurname(testUser.getSurname());
        dto.setPatronymic(testUser.getPatronymic());
        dto.setEmail(notExistEmail);
        dto.setRole(testUser.getRole());
        dto.setPassword("ff");

        assertTrue(service.registerUser(dto));
        verify(userDao,times(1)).create(userCaptor.capture());

        assertEquals(dto.getName(),userCaptor.getValue().getName());
        assertEquals(dto.getEmail(),userCaptor.getValue().getEmail());
        assertEquals(encoder.encode(""),userCaptor.getValue().getPasswordHash());
    }

    @Test
    public void testGetNumberOfRecords(){
        given(userDao.count()).willReturn(612L);
        assertEquals(612,service.getNumberOfRecords());
    }

    @Test
    public void testGetNumberOfPatients(){
        given(userDao.countPatients()).willReturn(612L);
        assertEquals(612,service.getNumberOfPatients());
    }

    @Test
    public void testFindPatientsToShow(){
        given(userDao.findPatientsForDoctorPage(5,5)).willReturn(patientsDtoList);
        assertEquals(patientsDtoList,service.findPatientsToShow(1,5));
    }
}