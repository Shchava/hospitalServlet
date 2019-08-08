package ua.training.servlet.hospital.service.diagnosis;

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

    DiagnosisDao diagnosisDao;

    public DiagnosisServiceImpl(DiagnosisDao diagnosisDao) {
        this.diagnosisDao = diagnosisDao;
    }

    @Override
    public List<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId) {
        return diagnosisDao.findDiagnosesByPatientId(pageNumber*DiagnosesPerPage,DiagnosesPerPage, patientId);
    }

    @Override
    public long getNumberOfDiagnosesByPatientId(long patientId) {
        return diagnosisDao.countDiagnosesOfPatient(patientId);
    }

    @Override
    public boolean addDiagnosis(DiagnosisDTO dto, long patientId, long doctorId) {
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

    @Override
    public Optional<Diagnosis> getDiagnosis(long idDiagnosis) {
        return diagnosisDao.findById(idDiagnosis);
    }

    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
