package in.techarray.billbuddy.user_service.dto;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String email;
    private String password;
}
