package in.techarray.billbuddy.user_service.service;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.model.Session;
import in.techarray.billbuddy.user_service.model.SessionStatus;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.SessionRepository;
import in.techarray.billbuddy.user_service.repository.UserRepository;

public class AuthService {

    private UserRepository userRepository;
    private SessionRepository sessionRepository;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    public ResponseEntity<UserDto> login( String email, String password ) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        
        if( userOptional.isEmpty() ) {
            return null;
        }
        User user = userOptional.get();

        if( !user.getPassword().equals(password) ) {
            return null;
        }
        String token = RandomStringUtils.secure().nextAlphanumeric(30);

        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
        session.setToken(token);
        session.setUser(user);
        sessionRepository.save(session);

        UserDto UserDto = new UserDto();

        MultiValueMapAdapter<String, String> header = new MultiValueMapAdapter<>();
        header.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);
    }
}
