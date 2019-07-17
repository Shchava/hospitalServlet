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

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import static org.mockito.Mockito.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCUserDaoTest {
    private static Server server;
    private static String dbName = "hospital";
    private static Connection connection;

    private static User user = new User("test", "test", "testPatronymic", "test@example.com", "password", Roles.PATIENT);

    @InjectMocks
    private UserDao dao = DaoFactory.getInstance().createUserDao();
    @Mock
    private Connection mockConnection;

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

        when(mockConnection.createStatement())
                .thenAnswer((Answer<Statement>) invocation -> connection.createStatement());

        when(mockConnection.prepareStatement(any(String.class)))
                .thenAnswer((Answer<PreparedStatement>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return connection.prepareStatement((String) args[0]);
                });

        when(mockConnection.prepareStatement(any(String.class), anyInt()))
                .thenAnswer((Answer<PreparedStatement>) invocation -> {
                    Object[] args = invocation.getArguments();
                    return connection.prepareStatement((String) args[0], (Integer) args[1]);
                });

        doAnswer(invocation -> {
            connection.close();
            return null;
        }).when(mockConnection).close();
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
}

