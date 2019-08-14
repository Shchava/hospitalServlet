package ua.training.servlet.hospital.service.procedure;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.entity.Diagnosis;
import ua.training.servlet.hospital.entity.Procedure;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ProcedureDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ProcedureServiceImpl implements ProcedureService {
    private static final Logger logger = LogManager.getLogger(ProcedureServiceImpl.class);

    private DaoFactory factory;

    public ProcedureServiceImpl(DaoFactory factory) {
        this.factory = factory;
    }

    @Override
    public long getNumberOfProceduresByDiagnosisId(long diagnosisId) {
        logger.debug("searching for number of procedures with diagnosis id " + diagnosisId);

        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.countProceduresOfDiagnosis(diagnosisId);
        }
    }

    @Override
    public List<Procedure> findProceduresByDiagnosisId(int pageNumber, int procedurePerPage, long diagnosisId) {
        logger.debug("searching for procedures with diagnosis id " + diagnosisId + " on page " + pageNumber + " with "
                + procedurePerPage + "entries on page");

        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.findProceduresWithDoctorByDiagnosisId(pageNumber * procedurePerPage, procedurePerPage, diagnosisId);
        }
    }

    @Override
    public boolean createProcedure(ProcedureDTO dto, long diagnosisId, long doctorId) {
        logger.info("trying to create procedure with name" + dto.getName() + "for diagnosis with id " + diagnosisId);
        Procedure toCreate = new Procedure();
        toCreate.setName(dto.getName());
        toCreate.setDescription(dto.getDescription());
        toCreate.setRoom(dto.getRoom());
        toCreate.setAppointmentDates(dto.getAppointmentDates());
        toCreate.setAssigned(getAssignedTime());
        toCreate.setDiagnosis(new Diagnosis(diagnosisId));
        toCreate.setAssignedBy(new User(doctorId));

        try (ProcedureDao procedureDao = factory.createProcedureDao()) {
            return procedureDao.create(toCreate);
        }
    }


    private LocalDateTime getAssignedTime() {
        return LocalDateTime.now();
    }
}
