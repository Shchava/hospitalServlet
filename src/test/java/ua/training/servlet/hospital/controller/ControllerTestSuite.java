package ua.training.servlet.hospital.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.controller.command.showdiagnosis.*;
import ua.training.servlet.hospital.controller.command.showpatient.AddDiagnosisTest;
import ua.training.servlet.hospital.controller.command.showpatient.ShowPatientDiagnosesTest;
import ua.training.servlet.hospital.controller.command.utilities.GetPathAttributeTest;
import ua.training.servlet.hospital.controller.command.utilities.GetSessionAttributeTest;
import ua.training.servlet.hospital.controller.filter.AuthFilter;
import ua.training.servlet.hospital.controller.filter.AuthFilterTest;
import ua.training.servlet.hospital.controller.filter.EncodingFilterTest;
import ua.training.servlet.hospital.controller.filter.LocalizationFilterTest;
import ua.training.servlet.hospital.controller.servlet.*;
import ua.training.servlet.hospital.controller.utilities.PaginationUtilityTest;
import ua.training.servlet.hospital.controller.utilities.SecurityUtility;
import ua.training.servlet.hospital.controller.utilities.SecurityUtilityTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StartPageTest.class,
        LoginTest.class,
        LogOutTest.class,
        RegistrationTest.class,
        ShowPatientTest.class,
        ShowAllPatientsTest.class,
        ShowPatientDiagnosesTest.class,
        AddDiagnosisTest.class,
        ShowDiagnosisTest.class,
        ShowMedicineTest.class,
        ShowProceduresTest.class,
        ShowSurgeriesTest.class,
        AddMedicineTest.class,
        AddProcedureTest.class,
        AddSurgeryTest.class,
        AuthFilterTest.class,
        LocalizationFilterTest.class,
        EncodingFilterTest.class,
        GetPathAttributeTest.class,
        GetSessionAttributeTest.class,
        PaginationUtilityTest.class,
        SecurityUtilityTest.class
})
public class ControllerTestSuite {
}
