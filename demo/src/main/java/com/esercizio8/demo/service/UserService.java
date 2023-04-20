package com.esercizio8.demo.service;
import com.esercizio8.demo.dto.requests.user.*;
import com.esercizio8.demo.dto.responses.user.UserCreateResponseDto;
import com.esercizio8.demo.dto.responses.user.UserPartialUpdateResponseDto;
import com.esercizio8.demo.dto.responses.user.UserUpdateResponseDto;
import com.esercizio8.demo.helper.UserHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import com.esercizio8.demo.model.User;
import com.esercizio8.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserHelper userHelper;

    public UserCreateResponseDto registerUser(UserCreateRequestDto registerUser) {
        userHelper.checkEmailAlreadyExists(registerUser.getEmail());
        User userSaved = userRepository.save(modelMapper.map(registerUser, User.class));
        return modelMapper.map(userSaved, UserCreateResponseDto.class);
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public UserUpdateResponseDto updateUser(UUID id,
                                            UserUpdateRequestDto updateUser) {
        User user = userHelper.getUser(id);

        if (!user.getEmail().equals(updateUser.getEmail())) {
            userHelper.checkEmailAlreadyExists(updateUser.getEmail());
            user.setEmail(updateUser.getEmail());
        }

        user.setFirstName(updateUser.getFirstname());
        user.setLastName(updateUser.getLastname());
        userRepository.save(user);
        return modelMapper.map(user, UserUpdateResponseDto.class);
    }
    
    public UserPartialUpdateResponseDto partialUpdateUser(UUID id,
                                                          UserPartialUpdateRequestDto partialUpdateUser) {
        User user = userHelper.getUser(id);

        if (partialUpdateUser.getFirstname() != null) {
            user.setFirstName(partialUpdateUser.getFirstname());
        }

        if (partialUpdateUser.getLastname() != null) {
            user.setLastName(partialUpdateUser.getLastname());
        }

        if (partialUpdateUser.getEmail() != null &&
            !partialUpdateUser.getEmail().equals(user.getEmail())) {

            userHelper.checkEmailAlreadyExists(partialUpdateUser.getEmail());
            user.setEmail(partialUpdateUser.getEmail());
        }

        userRepository.save(user);
        return modelMapper.map(user, UserPartialUpdateResponseDto.class);
    }
}

