package pl.jug.torun.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jug.torun.domain.Participant;
import pl.jug.torun.domain.ReceivedPrize;

import java.util.List;

@Repository
public interface ReceivedPrizeRepository extends JpaRepository<ReceivedPrize, Long> {

    List<ReceivedPrize> findByParticipant(Participant participant);

}
