package in.techarray.billbuddy.user_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import in.techarray.billbuddy.user_service.model.Session;
import java.util.Optional;


public interface SessionRepository extends JpaRepository<Session, UUID> {
    Optional<Session> findTopByTokenAndUser_IdOrderByLoginAtDesc(String token, UUID user_Id);
    Optional<Session> findTopByTokenOrderByLoginAtDesc(String token);
}
