package ua.training.servlet.hospital.dao.impl;



import ua.training.servlet.hospital.dao.*;
import ua.training.servlet.hospital.entity.Operation;

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

    @Override
    public ProcedureDao createProcedureDao(){
        return new JDBCProcedureDao(getConnection());
    }

    @Override
    public OperationDao createOperationDao() {
        return new JDBCOperationDao(getConnection());
    }

    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
