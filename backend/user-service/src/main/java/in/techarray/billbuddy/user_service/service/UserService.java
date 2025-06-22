package in.techarray.billbuddy.user_service.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.model.Role;
import in.techarray.billbuddy.user_service.model.User;
import in.techarray.billbuddy.user_service.repository.RoleRepository;
import in.techarray.billbuddy.user_service.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto getUserDetails( UUID ID ) {
        Optional<User> userOptional = userRepository.findById(ID);

        if( userOptional.isEmpty() ) {
            return null;
        }

        return UserDto.from(userOptional.get());
    }

    public UserDto setUserRole( UUID userId, List<UUID> roleIds ) {
        Optional<User> userOptional = userRepository.findById( userId );
        Set<Role> roles = roleRepository.findAllByIdIn( roleIds ); 

        if( userOptional.isEmpty() ) {
            return null;
        }

        User user = userOptional.get();
        user.setRoles( roles );

        User savedUser = userRepository.save(user);
        return UserDto.from(savedUser);
    }
}
