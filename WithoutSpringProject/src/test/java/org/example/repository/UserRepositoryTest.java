package org.example.repository;

import org.example.db.TestConnectionManager;
import org.example.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserRepositoryTest extends AbstractRepositoryTest {
    static UserRepository userRepository;
    static PostRepository postRepository;
    private User user;

    @BeforeAll
    static void beforeAll() {
        postgreSQLContainer.start();
        TestConnectionManager testConnectionManager = new TestConnectionManager(postgreSQLContainer);
        userRepository = new UserRepository(testConnectionManager);
        postRepository = new PostRepository(testConnectionManager, userRepository);
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setUsername("test_user");
    }

    @AfterAll
    static void afterAll() {
        postgreSQLContainer.stop();
    }

    @Test
    void testSaveSuccessful() {
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getId(), "Saved user should have an ID");
        assertEquals(user.getUsername(), savedUser.getUsername());
    }

    @Test
    void testFindByIdSuccessful() {
        userRepository.save(user);

        User foundUser = userRepository.findById(user.getId());

        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getUsername(), foundUser.getUsername());
    }

    @Test
    void testDeleteByIdSuccessful() {
        User savedUser =userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        assertFalse(userRepository.existsById(savedUser.getId()), "User should be deleted");
    }

    @Test
    void testFindAllSuccessful() {
        userRepository.save(user);

        User user2 = new User();
        user2.setUsername("user2");
        userRepository.save(user2);

        List<User> userList = userRepository.findAll();

        assertFalse(userList.isEmpty());
    }

    @Test
    void testExistsByIdSuccessful() {
        userRepository.save(user);

        assertTrue(userRepository.existsById(user.getId()));
    }

    @Test
    void testUpdateSuccessful() {
        userRepository.save(user);

        String updatedUsername = "updated_username";
        user.setUsername(updatedUsername);
        userRepository.update(user);

        User updatedUser = userRepository.findById(user.getId());
        assertEquals(updatedUsername, updatedUser.getUsername());
    }
}