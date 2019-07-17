package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.dao.impl.JDBCDaoFactory;
import ua.training.servlet.hospital.entity.Operation;

public abstract class DaoFactory {
    private static  DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract MedicineDao createMedicineDao();

    public abstract OperationDao createOperationDao();

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
