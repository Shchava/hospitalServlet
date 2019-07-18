package ua.training.servlet.hospital.dao.impl;

import org.h2.tools.RunScript;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLOutput;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDBCUserDaoTest.class,
        JDBCOperationDaoTest.class,
        JDBCMedicineDaoTest.class
})

public class JDBCTestSuite {
    static Connection connection;

    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
        Reader schema = new FileReader("src/test/resources/hospitalDatabaseSchema.sql");
        Reader data = new FileReader("src/test/resources/hospitalDatabaseData.sql");

        connection = DriverManager.getConnection("jdbc:h2:mem:hospital", "sa", "");

        RunScript.execute(connection, schema);
        RunScript.execute(connection, data);

    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }
}