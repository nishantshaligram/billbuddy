package in.techarray.billbuddy.user_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import in.techarray.billbuddy.user_service.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByTokenAndUser_Id(String token, Long userId);
}
