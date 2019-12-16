package uz.pc.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pc.collections.Filter;
import uz.pc.collections.SalaryCollection;
import uz.pc.collections.SalaryDetail;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Performer;
import uz.pc.db.entities.Production;
import uz.pc.db.repos.EmployeeRepository;
import uz.pc.db.repos.PerformerRepository;
import uz.pc.db.repos.ProductRepository;
import uz.pc.db.repos.ProductionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDAOImpl implements EmployeeDAO {

    private EmployeeRepository repository;
    private PerformerRepository performerRepository;
    private ProductionRepository productionRepository;
    private ProductRepository productRepository;


    @Autowired
    public EmployeeDAOImpl(
            EmployeeRepository repository,
            PerformerRepository performerRepository,
            ProductionRepository productionRepository,
            ProductRepository productRepository) {
        this.repository = repository;
        this.performerRepository = performerRepository;
        this.productionRepository = productionRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAllByOrderByIdDesc();
    }

    @Override
    public List<SalaryCollection> getAllForSalaries(Filter filter) {
        List<Employee> employees = this.getAll();
        List<SalaryCollection> salaries = new ArrayList<>();

        for (Employee emp : employees) {
            SalaryCollection salary = new SalaryCollection();
            salary.setEmployee(emp);

            List<Performer> performers = performerRepository.findAllByEmployeeId(emp.getId());
            for (Performer performer : performers) {
                SalaryDetail detail = new SalaryDetail();

                Production production = productionRepository.findByDateIsBetweenAndId(
                        filter.getStart(),
                        filter.getEnd(),
                        performer.getProductionId());

                if (production == null) break;
                detail.setProductionId(production.getId());
                detail.setProductionDate(production.getDate());
                detail.setProductionReference(production.getReference());
                detail.setWorkedHours(performer.getWorkedHours());
                detail.setSalary(performer.getSalary());
                detail.setProduct(productRepository.findById(production.getProductId()));

                salary.calcOverallSalary(performer.getSalary());
                salary.setOneDetail(detail);
            }

            salaries.add(salary);
        }

        return salaries;
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
