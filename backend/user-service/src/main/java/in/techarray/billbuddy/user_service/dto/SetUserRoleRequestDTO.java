package in.techarray.billbuddy.user_service.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetUserRoleRequestDTO {
    private List<UUID> roleIds;
}
