package com.esercizio8.demo.controller;
import com.esercizio8.demo.dto.requests.user.*;
import com.esercizio8.demo.dto.responses.user.UserCreateResponseDto;
import com.esercizio8.demo.dto.responses.user.UserPartialUpdateResponseDto;
import com.esercizio8.demo.dto.responses.user.UserUpdateResponseDto;
import com.esercizio8.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping("api/v1/user")
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
