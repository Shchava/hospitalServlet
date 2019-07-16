package ua.training.servlet.hospital.dao.mapper;



import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.enums.Roles;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ObjectMapper<User> {
    @Override
    public User extractFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id_user"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setPatronymic(rs.getString("patronymic"));
        user.setEmail(rs.getString("email"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setRole(Roles.valueOf(rs.getString("role")));
        return user;
    }
}
