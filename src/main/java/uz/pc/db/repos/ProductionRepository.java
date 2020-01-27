package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pc.db.entities.Production;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Integer> {

    Production getById(int id);
    Production getByReference(String reference);
    Production findByDateIsBetweenAndId(LocalDateTime start, LocalDateTime end, int id);
    List<Production> findAllByDateIsBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT sum(p.cost) AS total FROM Production p WHERE p.productId = :productId")
    int totalProductionByProduct(int productId);

    @Query("SELECT count(p.id) FROM Production p")
    int calculateCountOfProductions();

//    @Query("SELECT sum(p.cost) FROM Production p WHERE p.date BETWEEN :start AND :end")
//    int totalProductionBetweenDates(LocalDateTime start, LocalDateTime end);

}
