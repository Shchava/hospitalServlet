package ua.training.servlet.hospital.dao.impl.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementAction {
    boolean execute(PreparedStatement statement) throws SQLException;
}
