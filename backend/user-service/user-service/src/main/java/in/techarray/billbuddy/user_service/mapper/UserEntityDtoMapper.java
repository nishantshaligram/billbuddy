package in.techarray.billbuddy.user_service.mapper;

import in.techarray.billbuddy.user_service.dto.UserDto;

public class UserEntityDtoMapper {
    public static UserDto getUserDtoFromUserEntity(in.techarray.billbuddy.user_service.model.User user) {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        return userDto;
    }

}
