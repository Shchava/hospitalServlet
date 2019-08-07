package ua.training.servlet.hospital.service.medicine;

import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    List<Medicine> findMedicineByDiagnosisId(int pageNumber, int MedicinePerPage, long diagnosisId);
    Optional<Medicine> createMedicine(MedicineDTO dto, long diagnosisId, String doctorEmail);
}
