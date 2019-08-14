package ua.training.servlet.hospital.service.diagnosis;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(DiagnosisServiceImpl.class);

    DaoFactory factory;

    public DiagnosisServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public List<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId) {
        logger.debug("searching for diagnoses with patient id " + patientId + " on page " + pageNumber + " with "
                + DiagnosesPerPage + "entries on page");
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.findDiagnosesByPatientId(pageNumber * DiagnosesPerPage, DiagnosesPerPage, patientId);
        }
    }

    @Override
    public long getNumberOfDiagnosesByPatientId(long patientId) {
        logger.debug("searching for number of diagnoses with patient id " + patientId + " ");

        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.countDiagnosesOfPatient(patientId);
        }
    }

    @Override
    public boolean addDiagnosis(DiagnosisDTO dto, long patientId, long doctorId) {
        logger.info("trying to create diagnosis with name" + dto.getName() + "for user with id " + patientId);
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
        logger.debug("searching for diagnosis with id: " + idDiagnosis);
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.findById(idDiagnosis);
        }
    }

    @Override
    public boolean closeDiagnosis(long idDiagnosis) {
        logger.info("trying to close diagnosis with id: " + idDiagnosis);
        try (DiagnosisDao diagnosisDao = factory.createDiagnosisDao()) {
            return diagnosisDao.closeDiagnosis(idDiagnosis, getAssignedTime());
        }
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
