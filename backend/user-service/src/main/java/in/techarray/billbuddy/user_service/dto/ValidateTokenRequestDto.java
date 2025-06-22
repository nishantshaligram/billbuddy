package in.techarray.billbuddy.user_service.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequestDto {
    private String token;
    private UUID userId;
}
