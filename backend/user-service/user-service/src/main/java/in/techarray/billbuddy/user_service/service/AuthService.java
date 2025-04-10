package in.techarray.billbuddy.user_service.service;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.UserRepository;

@Service
public class AuthService {

    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto signUp(String email, String password) {  
        User user = new User();
        user.setEmail(email);
        user.setPassword(password); // In a real application, you should hash the password
        userRepository.save(user);
        return UserDto.from(user); 
    }
}
