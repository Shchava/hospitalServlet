package ua.training.servlet.hospital.dao.impl;

import ua.training.servlet.hospital.dao.GenericDao;
import ua.training.servlet.hospital.dao.mapper.ObjectMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JDBCGenericDao<E> implements GenericDao<E> {
    Connection connection;

    String CreateQuery;
    String FindByIDQuery;
    String FindRangeQuery;
    String FindAllQuery;
    String CountQuery;
    String CountColumnLabel;
    String UpdateQuery;
    int UpdateIdParameterIndex;
    String DeleteQuery;
    ObjectMapper<E> mapper;

    JDBCGenericDao(Connection connection, String createQuery, String findByIDQuery, String findRangeQuery, String findAllQuery, String countQuery, String countColumnLabel, String updateQuery, int updateIdParameterIndex, String deleteQuery, ObjectMapper<E> mapper) {
        this.connection = connection;
        CreateQuery = createQuery;
        FindByIDQuery = findByIDQuery;
        FindRangeQuery = findRangeQuery;
        FindAllQuery = findAllQuery;
        CountQuery = countQuery;
        CountColumnLabel = countColumnLabel;
        UpdateQuery = updateQuery;
        UpdateIdParameterIndex = updateIdParameterIndex;
        DeleteQuery = deleteQuery;
        this.mapper = mapper;
    }

    @Override
    public boolean create(E entity) {
        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(CreateQuery, Statement.RETURN_GENERATED_KEYS)) {

            int affected = insertIntoDb(statement,entity);
            if (affected == 1) {
                setId(entity, getId(entity, statement));
                created = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return created;
    }

    @Override
    public Optional<E> findById(long id) {
        E entity = null;

        try (PreparedStatement statement = connection.prepareStatement(FindByIDQuery)) {
            statement.setLong(1, id);
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
    public List<E> findRange(int start, int count){
        List<E> found = null;
        try (PreparedStatement statement = connection.prepareStatement(FindRangeQuery)){
            statement.setInt(1,start);
            statement.setInt(2,count);
            found = getAllFromStatement(statement);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return found;
    }


    @Override
    public List<E> findAll() {
        List<E> found = null;
        try (PreparedStatement statement = connection.prepareStatement(FindAllQuery)){
            found = getAllFromStatement(statement);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return found;
    }


    @Override
    public int count() {
        int count = 0;
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(CountQuery);
            if (rs.next()) {
                count = rs.getInt(CountColumnLabel);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean update(E entity) {
        boolean created = false;
        try (PreparedStatement statement = connection.prepareStatement(UpdateQuery, Statement.RETURN_GENERATED_KEYS)) {
            int affected = updateOnDb(statement,entity);
            created = affected == 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return created;
    }

    @Override
    public boolean delete(long id) {
        boolean affected = false;
        try (PreparedStatement statement = connection.prepareStatement(DeleteQuery)) {
            deleteEntity(statement,id);
            affected = true;
        } catch (Exception ex) {
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

    long getId(E entity, Statement statement) throws SQLException {

        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        } else {
            throw new IllegalArgumentException("generatedKeys is empty");
        }
    }

    List<E> getAllFromStatement(PreparedStatement statement) throws SQLException {
        List<E> entities = new ArrayList<>();
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            entities.add(extractEntity(rs));
        }
        return entities;
    }

    int insertIntoDb(PreparedStatement statement, E entity) throws SQLException {
        setEntityValues(statement, entity);
        return statement.executeUpdate();
    }

    int updateOnDb(PreparedStatement statement, E entity) throws SQLException {
        setEntityValues(statement, entity);
        statement.setLong(UpdateIdParameterIndex, getId(entity));
        return statement.executeUpdate();
    }

    E extractEntity(ResultSet rs) throws SQLException {
        return mapper.extractFromResultSet(rs);
    }

    void deleteEntity(PreparedStatement statement, long entityId) throws SQLException {
        statement.setLong(1, entityId);
        statement.execute();
    }

    abstract long getId(E entity);

    abstract void setId(E entity, long Id) throws SQLException;

    abstract void setEntityValues(PreparedStatement statement, E entity) throws SQLException;

}
