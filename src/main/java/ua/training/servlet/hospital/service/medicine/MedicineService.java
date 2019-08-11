package ua.training.servlet.hospital.service.medicine;

import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.dto.MedicineDTO;

import java.util.List;
import java.util.Optional;

public interface MedicineService {
    long getNumberOfMedicineByDiagnosisId(long diagnosisId);
    List<Medicine> findMedicineByDiagnosisId(int pageNumber, int MedicinePerPage, long diagnosisId);
    boolean createMedicine(MedicineDTO dto, long diagnosisId, long doctorId);
}
