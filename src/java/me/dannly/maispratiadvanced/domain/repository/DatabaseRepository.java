/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.dannly.maispratiadvanced.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import me.dannly.maispratiadvanced.domain.repository.factory.Conex;

/**
 *
 * @author vinix
 * @param <T>
 */
public abstract class DatabaseRepository<T> {

    private final Connection connection = Conex.getConnection();
    private PreparedStatement insertOrUpdate, delete, query;
    private final String queryString;

    public DatabaseRepository(String insertOrUpdate, String delete, String query) {
        queryString = query;
        try {
            this.insertOrUpdate = connection.prepareStatement(insertOrUpdate);
            this.delete = connection.prepareStatement(delete);
            this.query = connection.prepareStatement(query);
        } catch (SQLException ex) {
        }
    }

    public abstract Object[] setupInsert(T value);

    public abstract Object setupDelete(T value);

    public abstract T setupRead(ResultSet resultSet);

    private void prepareInsert(T value) {
        try {
            final Object[] values = setupInsert(value);
            for (int i = 1; i <= values.length; i++) {
                insertOrUpdate.setObject(i, values[i - 1]);
                if (i != 1) {
                    insertOrUpdate.setObject(values.length + i - 1, values[i - 1]);
                }
            }
        } catch (SQLException ex) {
        }
    }

    private void prepareDelete(T value) {
        try {
            delete.setObject(1, setupDelete(value));
        } catch (SQLException ex) {
        }
    }

    public void delete(T value) {
        try {
            prepareDelete(value);
            delete.executeUpdate();
            delete.close();
        } catch (SQLException ex) {
        }
    }

    public Integer insert(T value) {
        try {
            prepareInsert(value);
            System.out.println(insertOrUpdate.toString());
            final int row = insertOrUpdate.executeUpdate();
            insertOrUpdate.close();
            return row;
        } catch (SQLException ex) {
        }
        return null;
    }

    public List<T> getElements() {
        final List<T> list = new ArrayList<>();
        try (final ResultSet resultSet = query.executeQuery()) {
            while (resultSet.next()) {
                list.add(setupRead(resultSet));
            }
            resultSet.close();
            query.close();
        } catch (SQLException e) {
        }
        return list;
    }

    public List<T> getElementsByFilter(String filter, Object... parameters) {
        final List<T> list = new ArrayList<>();
        try (final PreparedStatement preparedStatement = connection.prepareStatement(queryString + " WHERE " + filter)) {
            for (int i = 1; i <= parameters.length; i++) {
                preparedStatement.setObject(i, parameters[i - 1]);
            }
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(setupRead(resultSet));
                }
                resultSet.close();
            }
            query.close();
        } catch (SQLException e) {
        }
        return list;
    }
}
