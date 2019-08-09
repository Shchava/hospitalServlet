package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.dao.mapper.ObjectMapper;
import ua.training.servlet.hospital.dao.mapper.ProcedureMapper;
import ua.training.servlet.hospital.entity.Procedure;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCProcedureDao extends JDBCGenericDao<Procedure> implements ProcedureDao {
    String createAssignmentDatesQuery = "INSERT INTO procedure_appointment_dates(procedure_id_therapy, appointment_dates) VALUES (?,?)";
    String deleteAppointmentDatesQuery = "DELETE FROM procedure_appointment_dates WHERE procedure_id_therapy = ?";
    String FindAppointmentDatesQuery = "SELECT appointment_dates from procedure_appointment_dates where procedure_id_therapy = ?";

    public JDBCProcedureDao(Connection connection) {
        super(
                connection,
                "INSERT INTO treatment(diagnosis,name, description, assigned, assigned_by_id_user, room) VALUES(?,?,?,?,?,?)",
                "SELECT * FROM treatment LEFT JOIN user ON(treatment.assigned_by_id_user = user.id_user) WHERE id_therapy = ?",
                "SELECT SQL_CALC_FOUND_ROWS * FROM treatment LEFT JOIN user ON(treatment.assigned_by_id_user = user.id_user) LIMIT ?,?",
                "SELECT * FROM treatment LEFT JOIN user ON(treatment.assigned_by_id_user = user.id_user)",
                "SELECT COUNT(*)FROM treatment",
                "COUNT(*)",
                "UPDATE treatment SET diagnosis = ?, name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, room = ? WHERE id_therapy = ?",
                7,
                "DELETE FROM treatment WHERE id_therapy = ?",
                new ProcedureMapper());
    }

    @Override
    public boolean create(Procedure entity) {
        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(CreateQuery, Statement.RETURN_GENERATED_KEYS)) {

            int affected = insertIntoDb(statement,entity);
            if (affected == 1) {
                setId(entity, getId(entity, statement));
                insertAppointmentDates(entity);
                created = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return created;
    }

    @Override
    int updateOnDb(PreparedStatement statement, Procedure entity) throws SQLException {
        setEntityValues(statement, entity);
        statement.setLong(UpdateIdParameterIndex, entity.getId());
        deleteAppointmentDates(entity.getId());
        insertAppointmentDates(entity);
        return statement.executeUpdate();
    }

    @Override
    Procedure extractEntity(ResultSet rs, ObjectMapper<Procedure> mapper) throws SQLException {
        Procedure extracted = mapper.extractFromResultSet(rs);
        extracted.setAppointmentDates(getAppointmentDates(extracted.getId()));
        return extracted;
    }

    @Override
    void deleteEntity(PreparedStatement statement, long id) throws SQLException {
        statement.setLong(1, id);
        deleteAppointmentDates(id);
        statement.execute();
    }

    @Override
    long getId(Procedure entity) {
        return entity.getId();
    }

    @Override
    void setId(Procedure entity, long Id) throws SQLException {
        entity.setId(Id);
    }


    @Override
    void setEntityValues(PreparedStatement statement, Procedure entity) throws SQLException {
        statement.setLong(1,entity.getDiagnosis());
        statement.setString(2,entity.getName());
        statement.setString(3,entity.getDescription());
        statement.setTimestamp(4, Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(5,entity.getAssignedBy().getId());
        statement.setInt(6,entity.getRoom());

    }

    private void insertAppointmentDates(Procedure from) throws SQLException {
        try (PreparedStatement insertAssignmentDates = connection.prepareStatement(createAssignmentDatesQuery)){
            insertAssignmentDates.setLong(1,from.getId());
            for(LocalDateTime date:from.getAppointmentDates()) {
                insertAssignmentDates.setObject(2, date);
                insertAssignmentDates.executeUpdate();
            }
        }
    }

    private void deleteAppointmentDates(long procedureId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(deleteAppointmentDatesQuery)) {
            statement.setLong(1, procedureId);
            statement.execute();
        }
    }

    private List<LocalDateTime> getAppointmentDates(long procedureId) throws SQLException {
        List<LocalDateTime> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(FindAppointmentDatesQuery)) {
            statement.setLong(1,procedureId);
            ResultSet rs =  statement.executeQuery();
            while (rs.next()) {
                result.add(rs.getTimestamp(1).toLocalDateTime());
            }
        }
        return result;
    }


}
