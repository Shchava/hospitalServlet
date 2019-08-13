package ua.training.servlet.hospital.service.diagnosis;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.DiagnosisDao;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.DiagnosisDTO;
import ua.training.servlet.hospital.service.user.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DiagnosisServiceImpl implements DiagnosisService {

    DaoFactory factory;

    public DiagnosisServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId) {
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.findDiagnosesByPatientId(pageNumber * DiagnosesPerPage, DiagnosesPerPage, patientId);
        }
    }

    @Override
    public long getNumberOfDiagnosesByPatientId(long patientId) {
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.countDiagnosesOfPatient(patientId);
        }
    }

    @Override
    public boolean addDiagnosis(DiagnosisDTO dto, long patientId, long doctorId) {
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.create(
                    new Diagnosis(
                            dto.getName(),
                            dto.getDescription(),
                            getAssignedTime(),
                            null,
                            new User(patientId),
                            new User(doctorId)
                    )
            );
        }
    }

    @Override
    public Optional<Diagnosis> getDiagnosis(long idDiagnosis) {
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.findById(idDiagnosis);
        }
    }

    @Override
    public boolean closeDiagnosis(long idDiagnosis) {
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.closeDiagnosis(idDiagnosis, getAssignedTime());
        }
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
