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
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCUserDaoTest {
    private static String dbName = "hospital";
    private static Connection connection;
    private UserDao dao;

    private static User user = new User(
            "JDBCMedicineDaoTest",
            "JDBCMedicineDaoTest",
            "testPatronymic",
            "JDBCMedicineDaoTest@example.com",
            "password",
            "testUserInfo",
            Roles.PATIENT);
    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

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

        dao = factory.createUserDao();
    }

    @Test
    public void test01Create() {
        assertTrue(dao.create(user));
        User dbUser = dao.findById(user.getId()).get();
        assertEquals(user, dbUser);
    }

    @Test
    public void test02FindByID() {
        User dbUser = dao.findById(user.getId()).get();
        assertTrue(user.equals(dbUser));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test03FindRange() {
        List<User> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(user));
    }

    @Test
    public void test04FindAll() {
        List<User> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(user));
    }

    @Test
    public void test05Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test06CountPatients() {
        assertEquals(3, dao.countPatients());
    }

    @Test
    public void test07Update() throws Exception {
        long id = user.getId();
        String newName = "test2";
        user.setName(newName);
        assertTrue(dao.update(user));
        assertEquals(id, user.getId());
        assertEquals(user, dao.findById(id).get());
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test08FindPatientsForDoctorPage(){
        List<ShowUserToDoctorDTO> patients = dao.findPatientsForDoctorPage(0,3);
        assertEquals(2, patients.size());
    }

    @Test
    public void test09FindByEmail() {
        assertEquals(dao.findById(2),dao.findByEmail("email2@example.com"));
        assertEquals(dao.findById(4),dao.findByEmail("email4@example.com"));
    }
    @Test
    public void test19Delete(){
        assertTrue(dao.delete(user.getId()));
        assertFalse(dao.findById(user.getId()).isPresent());
    }
}

