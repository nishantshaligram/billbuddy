package in.techarray.billbuddy.user_service.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends BaseModel {
    @Column( nullable=false, unique = true)
    private String email;
    @Column( nullable=false )
    private String password;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
