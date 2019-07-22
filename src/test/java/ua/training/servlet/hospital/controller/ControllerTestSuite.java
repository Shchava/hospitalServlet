package ua.training.servlet.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.servlet.LogOutTest;
import ua.training.servlet.hospital.controller.servlet.LoginTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        LogOutTest.class
})
public class ControllerTestSuite {
}
