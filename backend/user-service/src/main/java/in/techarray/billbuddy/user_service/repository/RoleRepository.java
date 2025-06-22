package in.techarray.billbuddy.user_service.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import in.techarray.billbuddy.user_service.model.Role;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Set<Role> findAllByIdIn( List<UUID> ids );
}
