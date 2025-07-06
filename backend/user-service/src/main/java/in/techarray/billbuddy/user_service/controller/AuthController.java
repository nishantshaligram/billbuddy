package in.techarray.billbuddy.user_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import in.techarray.billbuddy.user_service.dto.LoginRequestDto;
import in.techarray.billbuddy.user_service.dto.SignUpRequestDto;
import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.dto.ValidateTokenRequestDto;
import in.techarray.billbuddy.user_service.model.Session;
import in.techarray.billbuddy.user_service.model.SessionStatus;
import in.techarray.billbuddy.user_service.service.AuthService;

@RestController
@RequestMapping( "/auth" )
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/session")
    public ResponseEntity<List<Session>> getAllSessions(){
        return authService.getAllSessions();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto request) {
        UserDto UserDto = authService.signUp(request.getEmail(), request.getPassword());
        return new ResponseEntity<>(UserDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto request) {
        return authService.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/logout/{id}")
    public ResponseEntity<Void> logout(@PathVariable("id") UUID userId, @RequestHeader("token") String token) {
        return authService.logout( token, userId );
    }

    @PostMapping("/validate")
    public ResponseEntity<SessionStatus> validateToken(@RequestBody ValidateTokenRequestDto request ){
        SessionStatus sessionStatus = authService.validate( request.getToken(), request.getUserId() );
        return new ResponseEntity<>( sessionStatus, HttpStatus.OK );
    }
    
    @PostMapping("/validate/user")
    public ResponseEntity<UserDto> validateUser(@RequestHeader("cookie") String token ){
        UserDto userDto = authService.validateUser( token );
        return new ResponseEntity<>( userDto, HttpStatus.OK );
    }
}
