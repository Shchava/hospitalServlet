package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.dao.mapper.ObjectMapper;
import ua.training.servlet.hospital.dao.mapper.ShowUserToDoctorDTOMapper;
import ua.training.servlet.hospital.dao.mapper.UserMapper;
import ua.training.servlet.hospital.entity.User;
import ua.training.servlet.hospital.entity.dto.ShowUserToDoctorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao extends JDBCGenericDao<User> implements UserDao {
    private final String FindByEmailQuery = "SELECT * FROM user WHERE email = ?";
    private final String findPatientsForDoctorPageQuery = "SELECT user.id_user, user.name, user.surname, user.patronymic ,user.email, diagnosis.name " +
            "FROM user " +
            "LEFT JOIN diagnosis ON (user.id_user = diagnosis.patient_id_user) " +
            "WHERE ((user.id_user,diagnosis.assigned) IN (" +
            "SELECT checkD.patient_id_user, MAX(checkD.Assigned) FROM diagnosis AS checkD GROUP BY checkD.patient_id_user) " +
            "OR diagnosis.patient_id_user IS NULL) " +
            "AND role='PATIENT' " +
            "LIMIT ?,?";

    public JDBCUserDao(Connection connection) {
        super(
                connection,
                "INSERT INTO user(name, surname, patronymic, info, email, password_hash, role) VALUES(?,?,?,?,?,?,?)",
                "SELECT * FROM user WHERE id_user = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM user LIMIT ?,?",
                "SELECT * FROM user",
                "SELECT COUNT(*)FROM user",
                "COUNT(*)",
                "UPDATE user SET name = ?, surname = ?, patronymic = ?, info=?, email = ?, password_hash = ?, role = ? WHERE id_user = ?",
                8,
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
    void setEntityValues(PreparedStatement statement, User entity) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getSurname());
        statement.setString(3, entity.getPatronymic());
        statement.setString(4, entity.getInfo());
        statement.setString(5, entity.getEmail());
        statement.setString(6, entity.getPasswordHash());
        statement.setString(7, entity.getRole().name());
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

    @Override
    public List<ShowUserToDoctorDTO> findPatientsForDoctorPage(int start, int count) {
        List<ShowUserToDoctorDTO> found = null;

        try (PreparedStatement statement = connection.prepareStatement(findPatientsForDoctorPageQuery)) {
            statement.setInt(1, start);
            statement.setInt(2, count);
            found = getAllFromShowUserToDoctorDTOStatement(statement);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return found;
    }

    ;

    private List<ShowUserToDoctorDTO> getAllFromShowUserToDoctorDTOStatement(PreparedStatement statement) throws SQLException {
        ObjectMapper<ShowUserToDoctorDTO> showUserToDoctorDTOMapper = new ShowUserToDoctorDTOMapper();
        List<ShowUserToDoctorDTO> entities = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            entities.add(showUserToDoctorDTOMapper.extractFromResultSet(rs));
        }
        return entities;
    }

}
