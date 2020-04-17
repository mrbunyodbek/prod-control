package uz.pc.db.dao.interfaces;

import uz.pc.db.dto.Filter;
import uz.pc.db.dto.salary.SalariesDTO;
import uz.pc.db.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAll();
    List<SalariesDTO> getSalariesInformation(Filter filter);
    Employee getById(int id);
    Employee getIdByCardId(String cardId);
    boolean saveEmployee(Employee employee);
    boolean editEmployee(Employee employee);
    void deleteEmployee(int id);

}
