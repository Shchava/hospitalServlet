package ua.training.servlet.hospital.dao;

import ua.training.servlet.hospital.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static  DaoFactory daoFactory;

    public abstract UserDao createUserDao();

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
