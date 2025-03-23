package in.techarray.billbuddy.user_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import in.techarray.billbuddy.user_service.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
