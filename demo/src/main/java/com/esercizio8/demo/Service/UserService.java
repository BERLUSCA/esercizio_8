package com.esercizio8.demo.Service;
import com.esercizio8.demo.Dto.Requests.User.*;
import com.esercizio8.demo.Dto.Responses.User.UserCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserPartialUpdateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserUpdateResponseDto;
import com.esercizio8.demo.Exception.EmailAlreadyExistsException;
import com.esercizio8.demo.Exception.ResourceNotFoundException;
import com.esercizio8.demo.Helper.UserHelper;
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
    private final UserHelper userHelper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserHelper userHelper) {
        this.userRepository = userRepository;
        this.userHelper = userHelper;
    }

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
        User user = userHelper.checkUserAlreadyExists(id);

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
        User user = userHelper.checkUserAlreadyExists(id);

        if (!(partialUpdateUser.getFirstname() == null)) {
            user.setFirstName(partialUpdateUser.getFirstname());
        }

        if (!(partialUpdateUser.getLastname() == null)) {
            user.setLastName(partialUpdateUser.getLastname());
        }

        if (!(partialUpdateUser.getEmail() == null) &&
            !partialUpdateUser.getEmail().equals(user.getEmail())) {

            userHelper.checkEmailAlreadyExists(partialUpdateUser.getEmail());
            user.setEmail(partialUpdateUser.getEmail());
        }

        userRepository.save(user);

        return modelMapper.map(user, UserPartialUpdateResponseDto.class);
    }

}

