package in.techarray.billbuddy.user_service.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import in.techarray.billbuddy.user_service.model.Role;
import in.techarray.billbuddy.user_service.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private UUID id;
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
