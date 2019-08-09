package ua.training.servlet.hospital.service.surgery;

import ua.training.servlet.hospital.entity.Surgery;
import ua.training.servlet.hospital.entity.dto.SurgeryDTO;

import java.util.List;
import java.util.Optional;

public interface SurgeryService {
    long getNumberOfSurgeriesByDiagnosisId(long diagnosisId);
    List<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int SurgeriesPerPage, long diagnosisId);
    boolean createSurgery(SurgeryDTO dto, long diagnosisId, long doctorId);
}
