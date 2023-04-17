package com.esercizio8.demo.Controller;
import com.esercizio8.demo.Dto.Requests.User.*;
import com.esercizio8.demo.Dto.Responses.User.UserCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserPartialUpdateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserUpdateResponseDto;
import com.esercizio8.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/user")
@Validated
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) { this.userService = userService; }

    @PostMapping
    public UserCreateResponseDto createUser(@Valid @RequestBody UserCreateRequestDto user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@Valid @PathVariable("id") UUID id) { userService.deleteUserById(id); }

    @PutMapping("/{id}")
    public UserUpdateResponseDto updateUser(@Valid @PathVariable("id") UUID id,
                                            @Valid @RequestBody UserUpdateRequestDto user) {
        return userService.updateUser(user, id);
    }

    @PatchMapping("/{id}")
    public UserPartialUpdateResponseDto updatePartialUser(@Valid @PathVariable("id") UUID id,
                                                          @Valid @RequestBody UserPartialUpdateRequestDto user) {
        return userService.partialUpdateUser(user, id);
    }

}
