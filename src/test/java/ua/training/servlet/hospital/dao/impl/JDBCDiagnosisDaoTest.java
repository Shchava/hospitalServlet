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
import ua.training.servlet.hospital.dao.DiagnosisDao;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.User;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JDBCDiagnosisDaoTest {
    private static Server server;
    private static String dbName = "hospital";
    private static Connection connection;
    private static Diagnosis diagnosis = new Diagnosis(
            "testDiagnosisName",
            "testDiagnosisDescription",
            LocalDateTime.of(2019, 6, 20, 12, 30),
            LocalDateTime.of(2019, 8, 7, 10, 30));
    @InjectMocks
    DaoFactory factory = DaoFactory.getInstance();
    @Mock
    DataSource source;
    private DiagnosisDao dao;
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

        dao = factory.createDiagnosisDao();
        userDao = factory.createUserDao();


        User doctor = userDao.findById(1).get();
        User patient = new User();
        patient.setId(3);
        diagnosis.setDoctor(doctor);
        diagnosis.setPatient(patient);
    }

    @Test
    public void test1Create() {
        assertTrue(dao.create(diagnosis));
        Diagnosis dbDiagnosis = dao.findById(diagnosis.getIdDiagnosis()).get();
        assertEquals(diagnosis, dbDiagnosis);
    }

    @Test
    public void test2FindByID() {
        Diagnosis dbDiagnosis = dao.findById(diagnosis.getIdDiagnosis()).get();
        assertEquals(diagnosis, dbDiagnosis);
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test3FindRange() {
        List<Diagnosis> found = dao.findRange(4, 3);
        assertEquals(3, found.size());
        assertTrue(found.contains(diagnosis));
    }

    @Test
    public void test4FindAll() {
        List<Diagnosis> all = dao.findAll();
        assertEquals(7, all.size());
        assertTrue(all.contains(diagnosis));
    }

    @Test
    public void test5Count() {
        assertEquals(7, dao.count());
    }

    @Test
    public void test6CountDiagnosesOfPatient() {
        Assert.assertEquals(4, dao.countDiagnosesOfPatient(3));
    }

    @Test
    @Ignore //H2 does not support SQL_CALC_FOUND_ROWS
    public void test7FindPatientsForDoctorPage() {
        List<Diagnosis> diagnoses = dao.findDiagnosesByPatientId(0, 3, 3);
        assertEquals(4, diagnoses.size());
        assertTrue(diagnoses.contains(diagnosis));
    }

    @Test
    public void test8Update() {
        long id = diagnosis.getIdDiagnosis();
        String newName = "test2";
        diagnosis.setName(newName);
        assertTrue(dao.update(diagnosis));
        assertEquals(id, diagnosis.getIdDiagnosis());
        assertEquals(diagnosis, dao.findById(id).get());
    }

    @Test
    public void test9Delete() {
        assertTrue(dao.delete(diagnosis.getIdDiagnosis()));
        assertFalse(dao.findById(diagnosis.getIdDiagnosis()).isPresent());
    }
}