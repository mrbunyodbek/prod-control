package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Production;

import java.util.Date;

public interface ProductionRepository extends JpaRepository<Production, Integer> {

    Production getById(int id);
    Production getByReference(String reference);
    Production findByDateIsBetweenAndId(Date start, Date end, int id);

}
