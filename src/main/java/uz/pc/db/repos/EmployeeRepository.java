package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Employee findById(long id);

}
