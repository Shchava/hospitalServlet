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
import ua.training.servlet.hospital.dao.SurgeryDao;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Surgery;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCSurgeryDaoTest {
    private static Server server;
    private static String dbName = "hospital";
    private static Connection connection;

    private static User user = new User("testUserName", "testUserSurname", "testUserPatronymic", "JDBCMedicineDaoTest@example.com", "password", Roles.DOCTOR);
    private static Surgery surgery = new Surgery(new Diagnosis(1),"testOperationName","testDescription", LocalDateTime.now(),user, LocalDateTime.of(2019, Month.AUGUST,12,12,30));

    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

    private SurgeryDao dao;
    private UserDao userDao;


    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
        Reader schema = new FileReader("src/test/resources/hospitalDatabaseSchema.sql");
        Reader data = new FileReader("src/test/resources/hospitalDatabaseData.sql");

        connection = DriverManager.getConnection("jdbc:h2:mem:hospital", "sa", "");

        RunScript.execute(connection, schema);
        RunScript.execute(connection, data);

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

        dao = factory.createSurgeryDao();
        userDao = factory.createUserDao();
    }

    @Test
    public void test1Create() {
        userDao.create(user);
        assertTrue(dao.create(surgery));
        Surgery dbMedicine = dao.findById(surgery.getId()).get();
        assertEquals(surgery, dbMedicine);
    }

    @Test
    public void test2FindByID() {
        Surgery dbMedicine = dao.findById(surgery.getId()).get();
        assertTrue(surgery.equals(dbMedicine));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<Surgery> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(surgery));
    }

    @Test
    public void test4FindAll() {
        List<Surgery> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(surgery));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6CountDiagnosesOfPatient() {
        Assert.assertEquals(1, dao.countSurgeriesOfDiagnosis(1));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test7FindPatientsForDoctorPage(){
        List<Surgery> found = dao.findSurgeriesWithDoctorByDiagnosisId(0,5,3);
        assertEquals(4, found.size());
        assertTrue(found.contains(surgery));
    }

    @Test
    public void test8Update() {
        long id = surgery.getId();
        String newName = "test2";
        surgery.setName(newName);
        assertTrue(dao.update(surgery));
        assertEquals(id, surgery.getId());
        assertEquals(surgery, dao.findById(id).get());
    }

    @Test
    public void test9Delete(){
        assertTrue(dao.delete(surgery.getId()));
        assertFalse(dao.findById(surgery.getId()).isPresent());
    }
}