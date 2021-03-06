package ua.training.servlet.hospital.service.surgery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.SurgeryDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Surgery;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.SurgeryDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SurgeryServiceImpl implements SurgeryService {
    private static final Logger logger = LogManager.getLogger(SurgeryServiceImpl.class);

    private DaoFactory factory;

    public SurgeryServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public long getNumberOfSurgeriesByDiagnosisId(long diagnosisId) {
        logger.debug("searching for surgeries number of with diagnosis id " + diagnosisId);

        try (SurgeryDao surgeryDao = factory.createSurgeryDao()) {
            return surgeryDao.countSurgeriesOfDiagnosis(diagnosisId);
        }
    }

    @Override
    public List<Surgery> findSurgeriesByDiagnosisId(int pageNumber, int surgeryPerPage, long diagnosisId) {
        logger.debug("searching for surgeries with diagnosis id " + diagnosisId + " on page " + pageNumber + " with "
                + surgeryPerPage + "entries on page");
        try (SurgeryDao surgeryDao = factory.createSurgeryDao()) {
            return surgeryDao.findSurgeriesWithDoctorByDiagnosisId(pageNumber * surgeryPerPage, surgeryPerPage, diagnosisId);
        }
    }

    @Override
    public boolean createSurgery(SurgeryDTO dto, long diagnosisId, long doctorId) {
        logger.info("trying to create procedure with name" + dto.getName() + "for diagnosis with id " + diagnosisId);
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
