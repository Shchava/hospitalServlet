package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.dao.mapper.UserMapper;
import ua.training.servlet.hospital.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCGenericDao<User> implements UserDao {
    private String FindByEmailQuery = "SELECT * FROM user WHERE email = ?";

    public JDBCUserDao(Connection connection) {
        super(
                connection,
                "INSERT INTO user(name, surname, patronymic, email, password_hash, role) VALUES(?,?,?,?,?,?)",
                "SELECT * FROM user WHERE id_user = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM user LIMIT ?,?",
                "SELECT * FROM user",
                "SELECT COUNT(*)FROM user",
                "COUNT(*)",
                "UPDATE user SET name = ?, surname = ?, patronymic = ?, email = ?, password_hash = ?, role = ? WHERE id_user = ?",
                7,
                "DELETE FROM user WHERE id_user = ?",
                new UserMapper());
    }

    @Override
    long getId(User entity) {
        return entity.getId();
    }

    @Override
    void setId(User entity, long Id) throws SQLException {
        entity.setId(Id);
    }

    @Override
    void setEntityValues(PreparedStatement statement, User entity) throws SQLException{
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getSurname());
        statement.setString(3,entity.getPatronymic());
        statement.setString(4,entity.getEmail());
        statement.setString(5,entity.getPasswordHash());
        statement.setString(6,entity.getRole().name());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User entity = null;

        try (PreparedStatement statement = connection.prepareStatement(FindByEmailQuery)) {
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                entity = extractEntity(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }
}
