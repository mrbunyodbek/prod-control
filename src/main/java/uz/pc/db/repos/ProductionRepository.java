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

    @Query("SELECT sum(p.cost) AS total FROM Production p WHERE p.productId = :productId AND p.month = :month AND p.year = :year")
    int totalProductionByProduct(int productId, String month, Integer year);

    @Query("SELECT sum(p.amount) AS total FROM Production p WHERE p.productId = :productId AND p.month = :month AND p.year = :year")
    int totalAmountOfProductionByProduct(int productId, String month, Integer year);

    @Query("SELECT count(p.id) FROM Production p WHERE p.month = :month AND p.year = :year")
    int calculateCountOfProductions(String month, Integer year);

    @Query("SELECT sum(p.cost) FROM Production p WHERE p.month = :month AND p.year = :year")
    int calculateFullCostOfProductions(String month, Integer year);

//    @Query("SELECT sum(p.cost) FROM Production p WHERE p.date BETWEEN :start AND :end")
//    int totalProductionBetweenDates(LocalDateTime start, LocalDateTime end);

}
