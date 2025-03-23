package in.techarray.billbuddy.user_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.user_service.dto.UserRequestDTO;
import in.techarray.billbuddy.user_service.exception.ValidationException;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping( "api/users" )
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO userRequestDTO) throws ValidationException {
        User registeredUser = userService.registerUser(userRequestDTO);
        return ResponseEntity.ok(registeredUser);
    }
    
}
