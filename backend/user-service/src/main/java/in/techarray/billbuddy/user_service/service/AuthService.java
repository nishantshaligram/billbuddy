package in.techarray.billbuddy.user_service.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.exception.InvalidCredentialsException;
import in.techarray.billbuddy.user_service.exception.InvalidTokenException;
import in.techarray.billbuddy.user_service.exception.SessionNotFoundException;
import in.techarray.billbuddy.user_service.exception.UserNotFoundException;
import in.techarray.billbuddy.user_service.mapper.UserEntityDtoMapper;
import in.techarray.billbuddy.user_service.model.Session;
import in.techarray.billbuddy.user_service.model.SessionStatus;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.SessionRepository;
import in.techarray.billbuddy.user_service.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;

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
        MacAlgorithm algorithm = Jwts.SIG.HS256;
        SecretKey key = algorithm.key().build();
        Map<String, Object> claims = new HashMap<>();
        claims.put( "userId", user.getId());
        claims.put( "email", user.getEmail());
        claims.put( "roles", user.getRoles() );
        claims.put( "createdAt", new Date() );
        claims.put( "expiryAt", new Date( LocalDate.now().plusDays(3).toEpochDay() ) );

        String token = Jwts.builder()
                .claims(claims)
                .signWith(key, algorithm)
                .compact();
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
        headers.add(HttpHeaders.SET_COOKIE,  token);
        return new ResponseEntity<>(userDto, headers, HttpStatus.OK);
    }

    public ResponseEntity<Void> logout( String token, UUID userId ){
        Optional<Session> sessionOptional = sessionRepository.findByTokenAndUser_UUID(token, userId);

        if(sessionOptional.isEmpty()){
            throw new SessionNotFoundException("Session not found");
        }

        Session session = sessionOptional.get();
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    public SessionStatus validate(String token, UUID userId) {
        Optional<Session> sessOptional = sessionRepository.findByTokenAndUser_UUID(token, userId);
        if( sessOptional.isEmpty() || sessOptional.get().getSessionStatus().equals(SessionStatus.ENDED) ){
            throw new InvalidTokenException("token is invalid or expired");
        }
        return SessionStatus.ACTIVE;
    }
}
