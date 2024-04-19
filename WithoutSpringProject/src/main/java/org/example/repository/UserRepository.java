package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.entity.User;
import org.example.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User, Long> {
    private ConnectionManager connectionManager;

    public UserRepository(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public User save(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO  users (username) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getLong("id"));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id =?")) {
            ps.setLong(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    user = createUser(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return user;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            deleteResult = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return deleteResult;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(createUser(rs));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return users;
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    private User createUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setUsername(resultSet.getString("username"));
        return user;
    }
}
