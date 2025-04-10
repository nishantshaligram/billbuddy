package in.techarray.billbuddy.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.techarray.billbuddy.user_service.dto.SignUpRequestDto;
import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.service.AuthService;

@RestController
@RequestMapping( "/api/auth" )
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) {
        UserDto UserDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(UserDto, HttpStatus.OK);
    }

}
