package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.OperationDao;
import ua.training.servlet.hospital.dao.mapper.MedicineMapper;
import ua.training.servlet.hospital.dao.mapper.ObjectMapper;
import ua.training.servlet.hospital.dao.mapper.OperationMapper;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.Operation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCOperationDao implements OperationDao {
    private Connection connection;

    public JDBCOperationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Operation entity) {
        boolean created = false;
        final String query = "INSERT INTO operation(name, description, assigned, assigned_by_id_user, date) VALUES(?,?,?,?,?)";
        try(PreparedStatement statement =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            setEntityValues(statement,entity);

            int affected = statement.executeUpdate();
            if(affected == 1){
                getId(entity,statement);
                created = true;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return created;
    }

    @Override
    public Optional<Operation> findById(long id) {
        Operation entity = null;
        ObjectMapper<Operation> mapper = new OperationMapper();

        final String query = "SELECT * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user) WHERE id_therapy = " + id +";";
        try(Statement statement =  connection.createStatement()){
            ResultSet result = statement.executeQuery(query);
            if(result.next()){
                entity = mapper.extractFromResultSet(result);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.ofNullable(entity);
    }

    @Override
    public List<Operation> findRange(int start, int count) {
        final String query = "SELECT SQL_CALC_FOUND_ROWS * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user) LIMIT " + start + "," + count;
        return getAllFromQuery(query);
    }

    @Override
    public List<Operation> findAll() {
        final String query = "SELECT * FROM operation LEFT JOIN user ON(operation.assigned_by_id_user = user.id_user)";
        return getAllFromQuery(query);
    }


    @Override
    public int count() {
        int count = 0;
        final String query = "SELECT COUNT(*)FROM operation";
        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()){
                count = rs.getInt("COUNT(*)");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean update(Operation entity) {
        boolean created = false;
        final String query = "UPDATE operation SET name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, date = ? WHERE id_therapy = ?";
        try(PreparedStatement statement =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            setEntityValues(statement,entity);
            statement.setLong(6,entity.getId());

            int affected = statement.executeUpdate();
            created = affected == 1;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return created;
    }

    @Override
    public boolean delete(long id) {
        boolean affected = false;
        final String query = "DELETE FROM operation WHERE id_therapy = " + id + ";";
        try(Statement statement =  connection.createStatement()){
            statement.execute(query);
            affected = true;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return affected;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getId(Operation entity, Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            entity.setId(generatedKeys.getLong(1));
        }
    }

    private List<Operation> getAllFromQuery(String query){
        List<Operation> entities = new ArrayList<>();
        ObjectMapper<Operation> mapper = new OperationMapper();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Operation entity = mapper.extractFromResultSet(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return entities;
        }
        return entities;
    }

    private void setEntityValues(PreparedStatement statement, Operation entity) throws SQLException{
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getDescription());
        statement.setTimestamp(3,Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(4,entity.getAssignedBy().getId());
        statement.setObject(5,entity.getDate());
    }
}
