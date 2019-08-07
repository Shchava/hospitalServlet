package ua.training.servlet.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.filter.EncodingFilterTest;
import ua.training.servlet.hospital.controller.filter.LocalizationFilterTest;
import ua.training.servlet.hospital.controller.servlet.LogOutTest;
import ua.training.servlet.hospital.controller.servlet.LoginTest;
import ua.training.servlet.hospital.controller.servlet.RegistrationTest;
import ua.training.servlet.hospital.controller.servlet.ShowAllPatientsTest;
import ua.training.servlet.hospital.controller.utilities.PaginationUtilityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        LogOutTest.class,
        RegistrationTest.class,
        ShowAllPatientsTest.class,
        LocalizationFilterTest.class,
        EncodingFilterTest.class,
        PaginationUtilityTest.class
})
public class ControllerTestSuite {
}
