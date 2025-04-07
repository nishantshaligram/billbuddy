package in.techarray.billbuddy.user_service.dto;

import java.util.HashSet;
import java.util.Set;

import in.techarray.billbuddy.user_service.model.Role;
import in.techarray.billbuddy.user_service.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String email;
    private Set<Role> roles = new HashSet<>();

    public static UserDto from(User user) {
        UserDto UserDto = new UserDto();
        UserDto.setEmail(user.getEmail());
        UserDto.setRoles(user.getRoles());
        return UserDto;
    }
}
