package ua.training.servlet.hospital.service;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.medicine.MedicineService;
import ua.training.servlet.hospital.service.procedure.ProcedureService;
import ua.training.servlet.hospital.service.surgery.SurgeryService;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.AuthServiceImpl;
import ua.training.servlet.hospital.service.user.UserService;
import ua.training.servlet.hospital.service.user.UserServiceImpl;

public class DefaultServiceFactory extends ServiceFactory {
    @Override
    public UserService getUserService(UserDao dao) {
        return new UserServiceImpl(dao);
    }

    @Override
    public AuthService getAuthService(UserService service) {
        return new AuthServiceImpl(service);
    }

    @Override
    public DiagnosisService getDiagnosisService() {
        return null;
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
