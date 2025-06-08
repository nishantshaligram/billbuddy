package in.techarray.billbuddy.user_service.mapper;

import in.techarray.billbuddy.user_service.dto.UserDto;
import in.techarray.billbuddy.user_service.model.User;
public class UserEntityDtoMapper {
    public static UserDto getUserDtoFromUserEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
