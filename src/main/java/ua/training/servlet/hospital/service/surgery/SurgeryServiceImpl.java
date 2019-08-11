package ua.training.servlet.hospital.service.surgery;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.SurgeryDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Surgery;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.SurgeryDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SurgeryServiceImpl implements SurgeryService {
    private DaoFactory factory;

    public SurgeryServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public long getNumberOfSurgeriesByDiagnosisId(long diagnosisId) {
        try (SurgeryDao surgeryDao = factory.createSurgeryDao()) {
            return surgeryDao.countSurgeriesOfDiagnosis(diagnosisId);
        }
    }

    @Override
    public List<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int surgeryPerPage, long diagnosisId) {
        try (SurgeryDao surgeryDao = factory.createSurgeryDao()) {
            return surgeryDao.findSurgeriesWithDoctorByDiagnosisId(pageNumber * surgeryPerPage, surgeryPerPage, diagnosisId);
        }
    }

    @Override
    public boolean createSurgery(SurgeryDTO dto, long diagnosisId, long doctorId) {
        Surgery toCreate = new Surgery();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setDate(dto.getSurgeryDate());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(new Diagnosis(diagnosisId));
        toCreate.setAssignedBy(new User(doctorId));

        try (SurgeryDao surgeryDao = factory.createSurgeryDao()) {
            return surgeryDao.create(toCreate);
        }
    }


    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
