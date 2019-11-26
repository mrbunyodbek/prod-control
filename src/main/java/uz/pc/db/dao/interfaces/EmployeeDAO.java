package uz.pc.db.dao.interfaces;

import uz.pc.db.entities.Employee;

import java.util.List;

public interface EmployeeDAO {

    List<Employee> getAll();
    Employee getById(int id);
    void saveEmployee(Employee employee);
    void editEmployee(Employee employee);
    void deleteEmployee(int id);

}
