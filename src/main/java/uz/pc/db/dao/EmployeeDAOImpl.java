package uz.pc.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.db.entities.Employee;
import uz.pc.db.repos.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeDAOImpl implements EmployeeDAO {

    private EmployeeRepository repository;

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(int id) {
        return repository.findById(id);
    }


    @Override
    public void saveEmployee(Employee employee) {
        repository.save(employee);
    }

    @Override
    public void editEmployee(Employee employee) {
        Employee temp = repository.findById(employee.getId());
        temp.setSecondName(employee.getSecondName());
        temp.setFirstName(employee.getFirstName());
        temp.setExperience(employee.getExperience());

        repository.save(temp);
    }

    @Override
    public void deleteEmployee(int id) {
        repository.deleteById(id);
    }

}
