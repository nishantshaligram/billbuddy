package in.techarray.billbuddy.user_service.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserRequestDTO;
import in.techarray.billbuddy.user_service.exception.ValidationException;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    @Override
    public User registerUser(UserRequestDTO userRequestDTO) throws ValidationException {
        if( userRepository.findByEmail(userRequestDTO.getEmail()).isPresent()){
            throw new ValidationException("User with email already exists");
        }

        User user = new User();
        user.setEmail( userRequestDTO.getEmail());
        user.setPassword( passwordEncoder.encode( userRequestDTO.getPassword() ) );
        return userRepository.save(user);
    }

}
