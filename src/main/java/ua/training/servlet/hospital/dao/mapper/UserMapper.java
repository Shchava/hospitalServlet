package ua.training.servlet.hospital.dao.mapper;



import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("user.id_user"));
        user.setName(rs.getString("user.name"));
        user.setSurname(rs.getString("user.surname"));
        user.setPatronymic(rs.getString("user.patronymic"));
        user.setEmail(rs.getString("user.email"));
        user.setPasswordHash(rs.getString("user.password_hash"));
        user.setRole(Roles.valueOf(rs.getString("user.role")));
        return user;
    }
}
