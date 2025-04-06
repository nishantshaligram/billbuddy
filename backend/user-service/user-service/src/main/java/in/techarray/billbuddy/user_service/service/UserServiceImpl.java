package in.techarray.billbuddy.user_service.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserRequestDTO;
import in.techarray.billbuddy.user_service.exception.ValidationException;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }



    @Override
    public User registerUser(UserRequestDTO userRequestDTO) throws ValidationException {
        if( userRepository.findByUsername(userRequestDTO.getUsername()).isPresent()){
            throw new ValidationException("User with this username already exists");
        }

        User user = new User();
        user.setUsername( userRequestDTO.getUsername());
        user.setPassword( passwordEncoder.encode( userRequestDTO.getPassword() ) );
        return userRepository.save(user);
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow( () -> new UsernameNotFoundException( "User not found: " + username ));
    }

}
