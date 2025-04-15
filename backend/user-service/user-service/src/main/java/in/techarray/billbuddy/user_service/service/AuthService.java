package in.techarray.billbuddy.user_service.service;

import java.util.HashMap;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import in.techarray.billbuddy.user_service.dto.UserDto;
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

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public UserDto signUp(String email, String password) {  
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // In a real application, you should hash the password
        userRepository.save(user);
        return UserDto.from(user); 
    }

    public ResponseEntity<UserDto> login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){
            return null; // TODO: if user not found throw user not found exception.
        }

        User user = userOptional.get();

        if( ! user.getPassword().equals( password ) ) {
            return null; // TODO: if password is incorrect throw password mismatch exception.
        }

        String token = RandomStringUtils.secureStrong().nextAlphanumeric(30);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto userDto = UserEntityDtoMapper.getUserDtoFromUserEntity(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, token);
        ResponseEntity<UserDto> response = new ResponseEntity<>(userDto, headers, HttpStatus.OK);
        return response;
    }
}
