package in.techarray.billbuddy.user_service.service;

import in.techarray.billbuddy.user_service.dto.UserDto;
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

    public UserDto signUp(String email, String password) {  
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // In a real application, you should hash the password
        userRepository.save(user);
        return UserDto.from(user); 
    }
}
