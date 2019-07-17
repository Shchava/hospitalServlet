package ua.training.servlet.hospital.dao.impl;



import ua.training.servlet.hospital.dao.DaoFactory;
import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.dao.UserDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }

    @Override
    public MedicineDao createMedicineDao() {
        return new JDBCMedicineDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
