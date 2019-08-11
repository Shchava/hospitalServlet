package ua.training.servlet.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.command.showdiagnosis.*;
import ua.training.servlet.hospital.controller.command.utilities.GetPathAttributeTest;
import ua.training.servlet.hospital.controller.command.utilities.GetSessionAttributeTest;
import ua.training.servlet.hospital.controller.filter.EncodingFilterTest;
import ua.training.servlet.hospital.controller.filter.LocalizationFilterTest;
import ua.training.servlet.hospital.controller.servlet.*;
import ua.training.servlet.hospital.controller.utilities.PaginationUtilityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LoginTest.class,
        LogOutTest.class,
        RegistrationTest.class,
        ShowPatientTest.class,
        ShowAllPatientsTest.class,
        ShowDiagnosisTest.class,
        ShowMedicineTest.class,
        ShowProceduresTest.class,
        ShowSurgeriesTest.class,
        AddMedicineTest.class,
        LocalizationFilterTest.class,
        EncodingFilterTest.class,
        GetPathAttributeTest.class,
        GetSessionAttributeTest.class,
        PaginationUtilityTest.class
})
public class ControllerTestSuite {
}
