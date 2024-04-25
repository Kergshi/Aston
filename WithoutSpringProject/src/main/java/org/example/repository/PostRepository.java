package org.example.repository;

import org.example.db.ConnectionManager;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepository implements Repository<Post, Long> {
    private ConnectionManager connectionManager;
    private UserRepository userRepository;

    public PostRepository(ConnectionManager connectionManager, UserRepository userRepository) {
        this.connectionManager = connectionManager;
        this.userRepository = userRepository;
    }

    @Override
    public Post save(Post post) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO post (content,author_id) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setLong(2, post.getAuthor().getId());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RepositoryException("Failed to save post, no rows affected.");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getLong(1));
                } else {
                    throw new RepositoryException("Failed to save post, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return post;
    }

    @Override
    public Post findById(Long id) {
        Post post = new Post();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                post = createPost(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return post;
    }

    @Override
    public boolean deleteById(Long id) {
        boolean deleteResult;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM post WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            deleteResult = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return deleteResult;
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM post");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            posts = new ArrayList<>();
            while (resultSet.next()) {
                posts.add(createPost(resultSet));
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
        return posts;
    }

    @Override
    public boolean existsById(Long id) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM post WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void update(Post post) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE post SET content = ?, author_id = ? WHERE id = ?")) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setLong(2, post.getAuthor().getId());
            preparedStatement.setLong(3, post.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    private Post createPost(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getLong("id"));
        post.setContent(resultSet.getString("content"));
        long authorId = resultSet.getLong("author_id");
        User user = userRepository.findById(authorId);
        post.setAuthor(user);
        return post;
    }
}
