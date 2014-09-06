package pl.jug.torun.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jug.torun.domain.Draw;

@Repository
public interface DrawRepository extends JpaRepository<Draw, Long> {
}
