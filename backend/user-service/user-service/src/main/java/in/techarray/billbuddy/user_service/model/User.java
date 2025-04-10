package in.techarray.billbuddy.user_service.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
public class User extends BaseModel {
    private String email;
    private String password;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
