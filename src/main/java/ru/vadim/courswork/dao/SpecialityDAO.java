package ru.vadim.courswork.dao;

import ru.vadim.courswork.entities.Speciality;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class SpecialityDAO extends AbstractDAO<Speciality, Long> {
    // todo remove exceptions from signature
    public SpecialityDAO(String connectionString) throws SQLException {
        super(connectionString);
    }

    @Override
    public List<Speciality> getAll() throws SQLException {
        List<Speciality> lst = new LinkedList<>();
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select * from Speciality");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Speciality speciality = new Speciality();
                speciality.setId(rs.getInt(1));
                speciality.setName(rs.getString(2));
                speciality.setDescription(rs.getString(3));
                lst.add(speciality);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }

        return lst;
    }

    @Override
    public Speciality getEntityById(Long id) throws SQLException {
        Speciality speciality = null;
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("select * from Speciality where idSpeciality = ?");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                speciality = new Speciality();
                speciality.setId(rs.getInt(1));
                speciality.setName(rs.getString(2));
                speciality.setDescription(rs.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return speciality;
    }

    @Override
    public Speciality update(Speciality entity) throws SQLException {
        Speciality speciality = null;
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("update Speciality set name = ?, description = ? where id = ?");
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setLong(3, entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return speciality;
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("delete from Speciality where idSpeciality = ?");
            statement.setLong(1, id);
            return statement.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return false;
    }

    @Override
    public boolean create(Speciality entity) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = getPrepareStatement("insert into Speciality(name, description) values(?, ?)");
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closePrepareStatement(statement);
        }
        return statement.execute();
    }


}