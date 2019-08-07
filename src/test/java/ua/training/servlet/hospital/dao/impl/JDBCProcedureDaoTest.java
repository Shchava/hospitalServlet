package ua.training.servlet.hospital.dao.impl;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCProcedureDaoTest {
    private static Connection connection;

    private static User user = new User("testProcedureUserName", "testProcedureUserSurname", "testProcedureUserPatronymic", "JDBCProcedureDaoTest@example.com", "password", Roles.DOCTOR);
    static List<LocalDateTime> appointmentDates = Arrays.asList(
            LocalDateTime.of(2019, Month.AUGUST, 12, 14, 30),
            LocalDateTime.of(2019, Month.AUGUST, 14, 13, 30),
            LocalDateTime.of(2019, Month.AUGUST, 16, 14, 30),
            LocalDateTime.of(2019, Month.AUGUST, 19, 14, 0),
            LocalDateTime.of(2019, Month.AUGUST, 21, 15, 30));
    private static Procedure procedure = new Procedure(1,"testProcedureName", "testProcedureDescription", LocalDateTime.now(), user, 524, appointmentDates);
    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

    private ProcedureDao dao;
    private UserDao userDao;


    @BeforeClass
    public static void getConnection() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:hospital", "sa", "");
    }

    @AfterClass
    public static void closeConnection() throws Exception {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(source.getConnection())
                .thenAnswer((Answer<Connection>) invocation -> connection);

        dao = factory.createProcedureDao();
        userDao = factory.createUserDao();
    }

    @Test
    public void test1Create() {
        userDao.create(user);
        assertTrue(dao.create(procedure));
        Procedure dbProcedure = dao.findById(procedure.getId()).get();
        assertEquals(procedure, dbProcedure);
    }

    @Test
    public void test2FindByID() {
        Procedure dbProcedure = dao.findById(procedure.getId()).get();
        assertEquals(procedure, dbProcedure);
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<Procedure> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(procedure));
    }

    @Test
    public void test4FindAll() {
        List<Procedure> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(procedure));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6Update() {
        long id = procedure.getId();
        String newName = "test3";
        procedure.setName(newName);
        assertTrue(dao.update(procedure));
        assertEquals(id, procedure.getId());
        assertEquals(procedure, dao.findById(id).get());
    }

    @Test
    public void test7Delete() {
        assertTrue(dao.delete(procedure.getId()));
        assertFalse(dao.findById(procedure.getId()).isPresent());
    }
}