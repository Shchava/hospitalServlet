package ua.training.servlet.hospital.service;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.service.diagnosis.DiagnosisService;
import ua.training.servlet.hospital.service.medicine.MedicineService;
import ua.training.servlet.hospital.service.procedure.ProcedureService;
import ua.training.servlet.hospital.service.surgery.SurgeryService;
import ua.training.servlet.hospital.service.user.AuthService;
import ua.training.servlet.hospital.service.user.UserService;

public abstract class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public abstract UserService getUserService(UserDao dao);
    public abstract AuthService getAuthService(UserService userService);
    public abstract DiagnosisService getDiagnosisService();
    public abstract MedicineService getMedicineService();
    public abstract ProcedureService getProcedureService();
    public abstract SurgeryService getSurgeryService();

    public static ServiceFactory getInstance(){
        if( serviceFactory == null){
            synchronized (ServiceFactory.class) {
                if(serviceFactory == null) {
                    serviceFactory = new DefaultServiceFactory();
                }
            }
        }
        return serviceFactory;
    }
}
