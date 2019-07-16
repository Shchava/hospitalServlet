package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.UserDao;
import ua.training.servlet.hospital.dao.mapper.UserMapper;
import ua.training.servlet.hospital.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private Connection connection;

    JDBCUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(User entity) {
        boolean created = false;
        final String query = "INSERT INTO user(name, surname, patronymic, email, password_hash, role) VALUES(?,?,?,?,?,?)";
        try(PreparedStatement statement =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getSurname());
            statement.setString(3,entity.getPatronymic());
            statement.setString(4,entity.getEmail());
            statement.setString(5,entity.getPasswordHash());
            statement.setString(6,entity.getRole().name());

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
    public Optional<User> findById(long id) {
        User user = null;
        UserMapper mapper = new UserMapper();

        final String query = "SELECT * FROM user WHERE id_user = " + id +";";
        try(Statement statement =  connection.createStatement()){
            ResultSet result = statement.executeQuery(query);
            if(result.next()){
                user = mapper.extractFromResultSet(result);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findRange(int start, int count) {
        final String query = "SELECT SQL_CALC_FOUND_ROWS * FROM user LIMIT " + start + "," + count;
        return getAllFromQuery(query);
    }

    @Override
    public List<User> findAll() {
        final String query = "SELECT * FROM user";
        return getAllFromQuery(query);
    }


    @Override
    public int count() {
        int count = 0;
        final String query = "SELECT COUNT(*)FROM user";
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
    public boolean update(User entity) {
        boolean created = false;
        final String query = "UPDATE user SET name = ?, surname = ?, patronymic = ?, email = ?, password_hash = ?, role = ? WHERE id_user = ?";
        try(PreparedStatement statement =  connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1,entity.getName());
            statement.setString(2,entity.getSurname());
            statement.setString(3,entity.getPatronymic());
            statement.setString(4,entity.getEmail());
            statement.setString(5,entity.getPasswordHash());
            statement.setString(6,entity.getRole().name());
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
        final String query = "DELETE FROM user WHERE id_user = " + id + ";";
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

    private void getId(User user, Statement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            user.setId(generatedKeys.getLong(1));
        }
    }

    private List<User> getAllFromQuery(String query){
        List<User> users = new ArrayList<>();
        UserMapper mapper = new UserMapper();
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = mapper.extractFromResultSet(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return users;
        }
        return users;
    }

}
