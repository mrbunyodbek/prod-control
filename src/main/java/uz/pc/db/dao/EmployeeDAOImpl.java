package uz.pc.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pc.collections.Filter;
import uz.pc.collections.SalaryCollection;
import uz.pc.collections.DetailedSalary;
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
        List<Production> productions = productionRepository.findAllByDateIsBetween(filter.getStart(), filter.getEnd());
        List<Employee> employees = repository.findAll();

        List<SalaryCollection> salaries = new ArrayList<>();

        for (Employee employee : employees) {
            SalaryCollection salaryCollection = new SalaryCollection();
            salaryCollection.setEmployee(employee);

            for (Production production : productions) {
                DetailedSalary detailedSalary = new DetailedSalary();

                Performer performer = performerRepository.findByProductionIdAndEmployeeId(production.getId(), employee.getId());

                if (performer == null) continue;

                detailedSalary.setProductionId(production.getId());
                detailedSalary.setProductionDate(production.getDate());
                detailedSalary.setProductionReference(production.getReference());
                detailedSalary.setWorkedHours(performer.getWorkedHours());
                detailedSalary.setSalary(performer.getSalary());
                detailedSalary.setProduct(productRepository.findById(production.getProductId()));

                salaryCollection.calcOverallSalary(performer.getSalary());
                salaryCollection.setOneDetail(detailedSalary);
            }

            salaries.add(salaryCollection);
        }

        return salaries;
    }

    @Override
    public Employee getById(int id) {
        return repository.findById(id);
    }

    @Override
    public Employee getIdByCardId(String cardId) {
        return repository.findByCardId(cardId);
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

    private DetailedSalary collectSalaryForPerson(List<Production> productions, Employee employee) {


        return null;
    }

}
