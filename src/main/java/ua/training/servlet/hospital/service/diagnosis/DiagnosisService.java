package ua.training.servlet.hospital.service.diagnosis;

import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.dto.DiagnosisDTO;

import java.util.List;
import java.util.Optional;

public interface DiagnosisService {
    List<Diagnosis> findDiagnosesByPatientId(int pageNumber, int DiagnosesPerPage, long patientId);
    boolean addDiagnosis(DiagnosisDTO dto, long patientId, String doctorEmail);
    Optional<Diagnosis> getDiagnosis(long idDiagnosis);
}
