package uz.pc.db.dao.interfaces;

import uz.pc.collections.Filter;
import uz.pc.collections.SalaryCollection;
import uz.pc.db.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAll();
    List<SalaryCollection> getAllForSalaries(Filter filter);
    Employee getById(int id);
    void saveEmployee(Employee employee);
    void editEmployee(Employee employee);
    void deleteEmployee(int id);

}
