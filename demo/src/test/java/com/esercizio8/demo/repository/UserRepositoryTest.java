package com.esercizio8.demo.repository;
import com.esercizio8.demo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {
        @Autowired
        private UserRepository userRepository;

        @Test
        void shouldReturnTrueWhenEmailExists() {

            User user = new User();
            UUID userId = UUID.fromString("e0c16996-80df-4c44-a6e8-3cafae33a4c3");
            user.setId(userId);
            user.setEmail("test@example.com");
            user.setFirstName("giorgio");
            user.setLastName("gambino");

            userRepository.save(user);

            boolean exists = userRepository.existsByEmail("test@example.com");

            assertThat(exists).isTrue();
        }

        @Test
        void shouldReturnFalseWhenEmailDoesNotExist() {
            boolean exists = userRepository.existsByEmail("test@example.com");

            assertThat(exists).isFalse();
        }
    }

