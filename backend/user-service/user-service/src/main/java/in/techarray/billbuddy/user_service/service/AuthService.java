package in.techarray.billbuddy.user_service.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.exception.InvalidCredentialsException;
import in.techarray.billbuddy.user_service.exception.UserNotFoundException;
import in.techarray.billbuddy.user_service.mapper.UserEntityDtoMapper;
import in.techarray.billbuddy.user_service.model.Session;
import in.techarray.billbuddy.user_service.model.SessionStatus;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.SessionRepository;
import in.techarray.billbuddy.user_service.repository.UserRepository;

@Service
public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // TODO: Remove in Production.
    public ResponseEntity<List<Session>> getAllSessions(){
        List<Session> sessions = sessionRepository.findAll();
        return ResponseEntity.ok( sessions );
    }

    public UserDto signUp(String email, String password) {  
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password)); // In a real application, you should hash the password
        userRepository.save(user);
        return UserDto.from(user); 
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        // retrieve user details
        Optional<User> userOptional = userRepository.findByEmail(email);
        // check if user found
        if(userOptional.isEmpty()){
            throw new UserNotFoundException( "User with the given email address does not exist" );
        }
        User user = userOptional.get();
        // verify password matches
        if( ! bCryptPasswordEncoder.matches(password, user.getPassword()) ) {
            throw new InvalidCredentialsException( "Invalid credentials" );
        }
        // generate token
        String token = RandomStringUtils.secureStrong().nextAlphanumeric(30);
        // Create session
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        session.setLoginAt( new Date() );
        sessionRepository.save(session);
        // Create response
        UserDto userDto = UserEntityDtoMapper.getUserDtoFromUserEntity(user);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, token);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<Void> logout( Long userId, String token ){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_Id(token, userId);

        if(sessionOptional.isEmpty()){
            return null; // TODO: Throw proper error for session not found
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }
}
