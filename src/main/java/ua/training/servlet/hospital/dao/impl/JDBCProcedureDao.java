package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.ProcedureDao;
import ua.training.servlet.hospital.dao.mapper.ProcedureMapper;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Procedure;

import javax.swing.text.html.parser.Entity;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JDBCProcedureDao extends JDBCGenericDao<Procedure> implements ProcedureDao {
    private final String findProceduresByDiagnosisIdQuery =
            "SELECT * FROM treatment " +
                    "LEFT JOIN user ON user.id_user = treatment.assigned_by_id_user " +
                    "WHERE treatment.diagnosis = ?  LIMIT ?,?";
    private final String countProceduresByDiagnosisQuery = "SELECT COUNT(*)FROM treatment WHERE diagnosis = ";
    private final String proceduresCountLabel = "COUNT(*)";

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
    public boolean delete(long id) {
        boolean affected = false;
        try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
            affected = transaction(statement,id,this::deleteProcedures);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return affected;
    }
    boolean deleteProcedures(PreparedStatement statement, Long id) throws SQLException {
        statement.setLong(1, id);
        deleteAppointmentDates(id);
        return statement.executeUpdate() > 0;
    }
    @Override
    boolean createAction(PreparedStatement statement,Procedure entity) throws SQLException{
        if (insertIntoDb(statement,entity) == 1) {
            setId(entity, getId(entity, statement));
            insertAppointmentDates(entity);
            return true;
        }
        return false;
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
    Procedure extractEntity(ResultSet rs) throws SQLException {
        Procedure extracted = mapper.extractFromResultSet(rs);
        extracted.setAppointmentDates(getAppointmentDates(extracted.getId()));
        return extracted;
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
        statement.setLong(1,entity.getDiagnosis().getIdDiagnosis());
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


    @Override
    public List<Procedure> findProceduresWithDoctorByDiagnosisId(int start, int count, long diagnosisId) {
        List<Procedure> found = null;
        try (PreparedStatement statement = connection.prepareStatement(findProceduresByDiagnosisIdQuery)){
            statement.setLong(1,diagnosisId);
            statement.setInt(2,start);
            statement.setInt(3,count);
            found = getAllFromStatement(statement);
        }catch (Exception ex){
            ex.printStackTrace();
            found = new ArrayList<>();
        }
        return found;
    }

    @Override
    public long countProceduresOfDiagnosis(long diagnosisId) {
        return count(countProceduresByDiagnosisQuery + diagnosisId,proceduresCountLabel);
    }
}
