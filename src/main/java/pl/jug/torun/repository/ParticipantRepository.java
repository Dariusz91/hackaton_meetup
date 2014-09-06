package pl.jug.torun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jug.torun.domain.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByMemberId(String memberId);
}
