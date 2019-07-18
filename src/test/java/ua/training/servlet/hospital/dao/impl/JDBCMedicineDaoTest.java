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
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCMedicineDaoTest {
    private static Server server;
    private static String dbName = "hospital";
    private static Connection connection;

    private static User user = new User("testMedicineUserName", "testMedicineUserSurname", "testMedicineUserPatronymic", "JDBCMedicineDaoTestUser@example.com", "password", Roles.DOCTOR);
    private static Medicine medicine = new Medicine("testMedicineName","testDescription",LocalDateTime.now(),user,30,LocalDate.now().plusDays(30));

    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

    private MedicineDao dao;
    private UserDao userDao;


    @BeforeClass
    public static void getConnection() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:" + dbName, "sa", "");
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

        dao = factory.createMedicineDao();
        userDao = factory.createUserDao();
    }

    @Test
    public void test1Create() {
        userDao.create(user);
        assertTrue(dao.create(medicine));
        Medicine dbMedicine = dao.findById(medicine.getId()).get();
        assertEquals(medicine, dbMedicine);
    }

    @Test
    public void test2FindByID() {
        Medicine dbMedicine = dao.findById(medicine.getId()).get();
        assertTrue(medicine.equals(dbMedicine));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<Medicine> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(medicine));
    }

    @Test
    public void test4FindAll() {
        List<Medicine> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(medicine));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6Update() {
        long id = medicine.getId();
        String newName = "test2";
        medicine.setName(newName);
        assertTrue(dao.update(medicine));
        assertEquals(id, medicine.getId());
        assertEquals(medicine, dao.findById(id).get());
    }

    @Test
    public void test7Delete(){
        assertTrue(dao.delete(medicine.getId()));
        assertFalse(dao.findById(medicine.getId()).isPresent());
    }
}

