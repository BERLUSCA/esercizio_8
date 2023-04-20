package com.esercizio8.demo.helper;
import com.esercizio8.demo.exception.EmailAlreadyExistsException;
import com.esercizio8.demo.exception.ResourceNotFoundException;
import com.esercizio8.demo.model.User;
import com.esercizio8.demo.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.UUID;
@Component
public class UserHelper {

    private UserRepository userRepository;

    public UserHelper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("This email is already assigned, please insert another email");
        }
    }

    public User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
    }

}