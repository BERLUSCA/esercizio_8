package com.esercizio8.demo.Service;
import com.esercizio8.demo.Dto.Requests.User.*;
import com.esercizio8.demo.Dto.Responses.User.UserCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserPartialUpdateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserUpdateResponseDto;
import com.esercizio8.demo.Exception.EmailAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import com.esercizio8.demo.Model.User;
import com.esercizio8.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public UserCreateResponseDto registerUser(UserCreateRequestDto registerUser) {

        if (userRepository.existsByEmail(registerUser.getEmail())) {
            throw new EmailAlreadyExistsException("The email already exist, please insert another email");
        }

        User userSaved = userRepository.save(modelMapper.map(registerUser, User.class));
        return modelMapper.map(userSaved, UserCreateResponseDto.class);
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public UserUpdateResponseDto updateUser(UserUpdateRequestDto updateUser, UUID id) {
        User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));

        if (!user.getEmail().equals(updateUser.getEmail())) {
            if (userRepository.existsByEmail(updateUser.getEmail())) {
                throw new EmailAlreadyExistsException("This email is already assigned, please insert another email");
            }
            user.setEmail(updateUser.getEmail());
        }

        user.setFirstName(updateUser.getFirstname());
        user.setLastName(updateUser.getLastname());
        userRepository.save(user);

        return modelMapper.map(user, UserUpdateResponseDto.class);
    }
    
    public UserPartialUpdateResponseDto partialUpdateUser(UserPartialUpdateRequestDto partialUpdateUser, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found."));

        if (!Objects.isNull(partialUpdateUser.getFirstname())) {
            user.setFirstName(partialUpdateUser.getFirstname());
        }

        if (!Objects.isNull(partialUpdateUser.getLastname())) {
            user.setLastName(partialUpdateUser.getLastname());
        }

        if (!Objects.isNull(partialUpdateUser.getEmail()) && !partialUpdateUser.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(partialUpdateUser.getEmail())) {
                throw new EmailAlreadyExistsException("This email is already assigned, please insert another email");
            }
            user.setEmail(partialUpdateUser.getEmail());
        }

        userRepository.save(user);

        return modelMapper.map(user, UserPartialUpdateResponseDto.class);
    }

}

