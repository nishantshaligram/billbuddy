package in.techarray.billbuddy.user_service.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import in.techarray.billbuddy.user_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findAllByIdIn( List<Long> ids );
}
