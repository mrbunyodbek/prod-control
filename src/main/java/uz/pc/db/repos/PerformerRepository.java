package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Performer;

import java.util.List;

public interface PerformerRepository extends JpaRepository<Performer, Integer> {

    Performer getById(int id);
    List<Performer> findAllByProductionId(int id);
    List<Performer> findAllByEmployeeId(int id);
    Performer findByProductionIdAndEmployeeId(int productionId, int employeeId);
    void deleteAllByProductionId(int id);

}
