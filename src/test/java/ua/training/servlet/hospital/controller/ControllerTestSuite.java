package ua.training.servlet.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.filter.EncodingFilterTest;
import ua.training.servlet.hospital.controller.filter.LocalizationFilterTest;
import ua.training.servlet.hospital.controller.servlet.LogOutTest;
import ua.training.servlet.hospital.controller.servlet.LoginTest;
import ua.training.servlet.hospital.controller.servlet.RegistrationTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        LogOutTest.class,
        RegistrationTest.class,
        LocalizationFilterTest.class,
        EncodingFilterTest.class
})
public class ControllerTestSuite {
}
