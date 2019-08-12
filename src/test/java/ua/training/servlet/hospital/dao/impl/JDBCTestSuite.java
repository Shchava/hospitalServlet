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

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDBCUserDaoTest.class,
        JDBCSurgeryDaoTest.class,
        JDBCMedicineDaoTest.class,
        JDBCProcedureDaoTest.class,
        JDBCDiagnosisDaoTest.class
})

public class JDBCTestSuite {



}