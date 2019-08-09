package ua.training.servlet.hospital;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.ControllerTestSuite;
import ua.training.servlet.hospital.dao.impl.JDBCTestSuite;
import ua.training.servlet.hospital.service.ServicesTestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JDBCTestSuite.class,
        ServicesTestSuite.class,
        ControllerTestSuite.class
})
public class GeneralTestSuite {
}
