package ua.training.servlet.hospital.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisServiceImplTest;
import ua.training.servlet.hospital.service.medicine.MedicineServiceImplTest;
import ua.training.servlet.hospital.service.procedure.ProcedureServiceImplTest;
import ua.training.servlet.hospital.service.surgery.SurgeryServiceImplTest;
import ua.training.servlet.hospital.service.user.AuthServiceImplTest;
import ua.training.servlet.hospital.service.user.UserServiceImplTest;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthServiceImplTest.class,
        UserServiceImplTest.class,
        DiagnosisServiceImplTest.class,
        MedicineServiceImplTest.class,
        ProcedureServiceImplTest.class,
        SurgeryServiceImplTest.class
})
public class ServicesTestSuite {
}
