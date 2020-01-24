package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pc.db.entities.Employee;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByOrderByIdDesc();
    Employee findById(int id);

    @Query("SELECT e.id FROM Employee e WHERE e.cardId = :id")
    int getIdOfEmployeeByCardId(String id);

}
