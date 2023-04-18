package com.esercizio8.demo.Controller;
import com.esercizio8.demo.Dto.Requests.User.*;
import com.esercizio8.demo.Dto.Responses.User.UserCreateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserPartialUpdateResponseDto;
import com.esercizio8.demo.Dto.Responses.User.UserUpdateResponseDto;
import com.esercizio8.demo.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("api/v1/user")
@Validated
@RestController
public class UserController {

    private final UserService userService;

    public UserController (UserService userService) { this.userService = userService; }

    @PostMapping
    public UserCreateResponseDto createUser(@Valid @RequestBody UserCreateRequestDto user) {
        return userService.registerUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) { userService.deleteUserById(id); }

    @PutMapping("/{id}")
    public UserUpdateResponseDto updateUser(@PathVariable("id") UUID id,
                                            @Valid @RequestBody UserUpdateRequestDto user) {
        return userService.updateUser(id, user);
    }

    @PatchMapping("/{id}")
    public UserPartialUpdateResponseDto updatePartialUser(@PathVariable("id") UUID id,
                                                          @Valid @RequestBody UserPartialUpdateRequestDto user) {
        return userService.partialUpdateUser(id, user);
    }

}
