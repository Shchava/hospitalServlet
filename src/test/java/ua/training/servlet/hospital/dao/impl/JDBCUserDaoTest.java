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
import ua.training.servlet.hospital.entity.enums.Roles;

import javax.sql.DataSource;
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

    private static User user = new User("JDBCMedicineDaoTest", "JDBCMedicineDaoTest", "testPatronymic", "JDBCMedicineDaoTest@example.com", "password","testUserInfo", Roles.PATIENT);
    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();

    @Mock
    DataSource source;

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

        dao = factory.createUserDao();
    }

    @Test
    public void test1Create() {
        assertTrue(dao.create(user));
        User dbUser = dao.findById(user.getId()).get();
        assertEquals(user, dbUser);
    }

    @Test
    public void test2FindByID() {
        User dbUser = dao.findById(user.getId()).get();
        assertTrue(user.equals(dbUser));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<User> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(user));
    }

    @Test
    public void test4FindAll() {
        List<User> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(user));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6Update() throws Exception {
        long id = user.getId();
        String newName = "test2";
        user.setName(newName);
        assertTrue(dao.update(user));
        assertEquals(id, user.getId());
        assertEquals(user, dao.findById(id).get());
    }

    @Test
    public void test7Delete(){
        assertTrue(dao.delete(user.getId()));
        assertFalse(dao.findById(user.getId()).isPresent());
    }

    @Test
    public void findByEmail() {
        assertEquals(dao.findById(2),dao.findByEmail("email2@example.com"));
        assertEquals(dao.findById(4),dao.findByEmail("email4@example.com"));
    }
}

