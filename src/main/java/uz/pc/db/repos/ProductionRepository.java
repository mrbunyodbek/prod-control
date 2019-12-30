package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Production;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Integer> {

    Production getById(int id);
    Production getByReference(String reference);
    Production findByDateIsBetweenAndId(LocalDateTime start, LocalDateTime end, int id);
    List<Production> findAllByDateIsBetween(LocalDateTime start, LocalDateTime end);

}
