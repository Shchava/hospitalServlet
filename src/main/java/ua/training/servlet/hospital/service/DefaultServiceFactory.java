package ua.training.servlet.hospital.service;

import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisServiceImpl;
import ua.training.servlet.hospital.service.medicine.MedicineService;
import ua.training.servlet.hospital.service.medicine.MedicineServiceImpl;
import ua.training.servlet.hospital.service.procedure.ProcedureService;
import ua.training.servlet.hospital.service.procedure.ProcedureServiceImpl;
import ua.training.servlet.hospital.service.surgery.SurgeryService;
import ua.training.servlet.hospital.service.surgery.SurgeryServiceImpl;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.AuthServiceImpl;
import ua.training.servlet.hospital.service.user.UserService;
import ua.training.servlet.hospital.service.user.UserServiceImpl;

public class DefaultServiceFactory extends ServiceFactory {
    private DaoFactory daoFactory = DaoFactory.getInstance();

    private volatile UserService userService;
    private volatile AuthService authService;
    private volatile DiagnosisService diagnosisService;
    private volatile MedicineService medicineService;
    private volatile ProcedureService procedureService;
    private volatile SurgeryService surgeryService;

    @Override
    public UserService getUserService() {
        if (userService == null) {
            synchronized (UserService.class) {
                if (userService == null) {
                    userService = new UserServiceImpl(daoFactory);
                }
            }
        }
        return userService;
    }

    @Override
    public AuthService getAuthService() {
        if (authService == null) {
            synchronized (AuthService.class) {
                if (authService == null) {
                    authService = new AuthServiceImpl(getUserService());
                }
            }
        }
        return authService;
    }

    @Override
    public DiagnosisService getDiagnosisService() {
        if (diagnosisService == null) {
            synchronized (DiagnosisService.class) {
                if (diagnosisService == null) {
                    diagnosisService = new DiagnosisServiceImpl(daoFactory);
                }
            }
        }
        return diagnosisService;
    }

    @Override
    public MedicineService getMedicineService() {
        if (medicineService == null) {
            synchronized (MedicineService.class) {
                if (medicineService == null) {
                    medicineService = new MedicineServiceImpl(daoFactory);
                }
            }
        }
        return medicineService;
    }

    @Override
    public ProcedureService getProcedureService() {
        if (procedureService == null) {
            synchronized (ProcedureService.class) {
                if (procedureService == null) {
                    procedureService = new ProcedureServiceImpl(daoFactory);
                }
            }
        }
        return procedureService;
    }

    @Override
    public SurgeryService getSurgeryService() {
        if (surgeryService == null) {
            synchronized (SurgeryService.class) {
                if (surgeryService == null) {
                    surgeryService = new SurgeryServiceImpl(daoFactory);
                }
            }
        }
        return surgeryService;
    }

}
