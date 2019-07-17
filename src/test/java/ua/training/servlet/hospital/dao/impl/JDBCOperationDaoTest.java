package ua.training.servlet.hospital.dao.impl;

import org.h2.tools.RunScript;
import org.h2.tools.Server;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.dao.OperationDao;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Operation;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCOperationDaoTest {
    private static Server server;
    private static String dbName = "hospital";
    private static Connection connection;

    private static User user = new User("testUserName", "testUserSurname", "testUserPatronymic", "JDBCMedicineDaoTest@example.com", "password", Roles.DOCTOR);
    private static Operation operation = new Operation("testOperationName","testDescription", LocalDateTime.now(),user, LocalDateTime.of(2019, Month.AUGUST,12,12,30));

    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

    private OperationDao dao;
    private UserDao userDao;

    @BeforeClass
    public static void initDB() throws Exception {
        server = Server.createTcpServer("-tcpAllowOthers").start();

        connection = DriverManager.getConnection("jdbc:h2:mem:" + dbName, "sa", "");

        Reader schema = new FileReader("src/test/resources/hospitalDatabaseSchema.sql");
        Reader data = new FileReader("src/test/resources/hospitalDatabaseData.sql");

        RunScript.execute(connection, schema);
        RunScript.execute(connection, data);
    }

    @AfterClass
    public static void stopDB() throws Exception {
        server.stop();
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(source.getConnection())
                .thenAnswer((Answer<Connection>) invocation -> connection);

        dao = factory.createOperationDao();
        userDao = factory.createUserDao();
    }

    @Test
    public void test1Create() {
        userDao.create(user);
        assertTrue(dao.create(operation));
        Operation dbMedicine = dao.findById(operation.getId()).get();
        assertEquals(operation, dbMedicine);
    }

    @Test
    public void test2FindByID() {
        Operation dbMedicine = dao.findById(operation.getId()).get();
        assertTrue(operation.equals(dbMedicine));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<Operation> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(operation));
    }

    @Test
    public void test4FindAll() {
        List<Operation> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(operation));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6Update() {
        long id = operation.getId();
        String newName = "test2";
        operation.setName(newName);
        assertTrue(dao.update(operation));
        assertEquals(id, operation.getId());
        assertEquals(operation, dao.findById(id).get());
    }

    @Test
    public void test7Delete(){
        assertTrue(dao.delete(operation.getId()));
        assertFalse(dao.findById(operation.getId()).isPresent());
    }
}