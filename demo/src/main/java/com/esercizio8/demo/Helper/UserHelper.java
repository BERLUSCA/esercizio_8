package com.esercizio8.demo.Helper;
import com.esercizio8.demo.Exception.EmailAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Model.User;
import com.esercizio8.demo.Repository.UserRepository;
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

    public User checkUserAlreadyExists(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));
    }

}