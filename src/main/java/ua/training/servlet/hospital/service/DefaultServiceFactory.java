package ua.training.servlet.hospital.service;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisServiceImpl;
import ua.training.servlet.hospital.service.medicine.MedicineService;
import ua.training.servlet.hospital.service.procedure.ProcedureService;
import ua.training.servlet.hospital.service.surgery.SurgeryService;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.AuthServiceImpl;
import ua.training.servlet.hospital.service.user.UserService;
import ua.training.servlet.hospital.service.user.UserServiceImpl;

public class DefaultServiceFactory extends ServiceFactory {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    @Override
    public UserService getUserService() {
        return new UserServiceImpl(daoFactory.createUserDao());
    }

    @Override
    public AuthService getAuthService() {
        return new AuthServiceImpl(getUserService());
    }

    @Override
    public DiagnosisService getDiagnosisService() {
        return new DiagnosisServiceImpl(daoFactory.createDiagnosisDao());
    }

    @Override
    public MedicineService getMedicineService() {
        return null;
    }

    @Override
    public ProcedureService getProcedureService() {
        return null;
    }

    @Override
    public SurgeryService getSurgeryService() {
        return null;
    }

}
