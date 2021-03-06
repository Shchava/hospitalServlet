package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static volatile DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract DiagnosisDao createDiagnosisDao();

    public abstract MedicineDao createMedicineDao();

    public abstract SurgeryDao createSurgeryDao();

    public abstract ProcedureDao createProcedureDao();

    public static synchronized DaoFactory getInstance(){
        if( daoFactory == null){
            synchronized (DaoFactory.class) {
                if(daoFactory == null) {
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }
}
