package pl.jug.torun.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jug.torun.domain.PrizeDefinition;

@Repository
public interface PrizeDefinitionRepository extends JpaRepository<PrizeDefinition, Long> {
}
