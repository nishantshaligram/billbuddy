package in.techarray.billbuddy.user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import in.techarray.billbuddy.user_service.model.Session;

public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findByTokenAndUser_UUID(String token, UUID userId);
}
