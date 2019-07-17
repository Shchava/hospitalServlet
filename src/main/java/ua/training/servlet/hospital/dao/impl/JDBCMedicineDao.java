package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.MedicineDao;
import ua.training.servlet.hospital.dao.mapper.MedicineMapper;
import ua.training.servlet.hospital.dao.mapper.ObjectMapper;
import ua.training.servlet.hospital.dao.mapper.UserMapper;
import ua.training.servlet.hospital.entity.Medicine;
import ua.training.servlet.hospital.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCMedicineDao implements MedicineDao {
    private Connection connection;

    public JDBCMedicineDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(Medicine entity) {
        boolean created = false;
        final String query = "INSERT INTO medicine(name, description, assigned, assigned_by_id_user, count, refill) VALUES(?,?,?,?,?,?)";
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
    public Optional<Medicine> findById(long id) {
        Medicine entity = null;
        ObjectMapper<Medicine> mapper = new MedicineMapper();

        final String query = "SELECT * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user) WHERE id_therapy = " + id +";";
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
    public List<Medicine> findRange(int start, int count) {
        final String query = "SELECT SQL_CALC_FOUND_ROWS * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user) LIMIT " + start + "," + count;
        return getAllFromQuery(query);
    }

    @Override
    public List<Medicine> findAll() {
        final String query = "SELECT * FROM medicine LEFT JOIN user ON(medicine.assigned_by_id_user = user.id_user)";
        return getAllFromQuery(query);
    }


    @Override
    public int count() {
        int count = 0;
        final String query = "SELECT COUNT(*)FROM medicine";
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
    public boolean update(Medicine entity) {
        boolean created = false;
        final String query = "UPDATE medicine SET name = ?, description = ?, assigned = ?, assigned_by_id_user = ?, count = ?, refill = ? WHERE id_therapy = ?";
        try(PreparedStatement statement =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            setEntityValues(statement,entity);
            statement.setLong(7,entity.getId());

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
        final String query = "DELETE FROM medicine WHERE id_therapy = " + id + ";";
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

    private void getId(Medicine medicine, Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            medicine.setId(generatedKeys.getLong(1));
        }
    }

    private List<Medicine> getAllFromQuery(String query){
        List<Medicine> entities = new ArrayList<>();
        ObjectMapper<Medicine> mapper = new MedicineMapper();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Medicine entity = mapper.extractFromResultSet(rs);
                entities.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return entities;
        }
        return entities;
    }

    private void setEntityValues(PreparedStatement statement, Medicine entity) throws SQLException{
        statement.setString(1,entity.getName());
        statement.setString(2,entity.getDescription());
        statement.setTimestamp(3,Timestamp.valueOf(entity.getAssigned()));
        statement.setLong(4,entity.getAssignedBy().getId());
        statement.setInt(5,entity.getCount());
        statement.setObject(6,entity.getRefill());
    }
}
