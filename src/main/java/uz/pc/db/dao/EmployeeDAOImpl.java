package uz.pc.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDAOImpl implements EmployeeDAO {

    private EmployeeRepository repository;
    private EntityManager em;

    private Logger logger = LoggerFactory.getLogger(EmployeeDAOImpl.class);

    @Autowired
    public EmployeeDAOImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAllByOrderByIdDesc();
    }

    /**
     * Method collects full information from database and makes SalaryCollection list for report.
     *
     * @param filter Chosen two dates, between which will be retrieved data.
     * @return List of salary collections.
     */
    @Override
    @Transactional
    public List<SalaryCollection> getSalariesInformation(Filter filter) {
        List<SalaryCollection> fullCollection = new ArrayList<>();

        String GET_DATA = String.format("SELECT de.id, de.firstName, de.secondName, prod.date, prod.reference, d.name, pr.experience, pr.workedHours, pr.salary FROM Performer pr INNER JOIN Employee de on pr.employeeId = de.id INNER JOIN Production prod on pr.productionId = prod.id INNER JOIN Product d ON d.id = prod.productId WHERE prod.date BETWEEN '%s' AND '%s' ORDER BY de.id DESC", filter.getStart(), filter.getEnd());
        Query query = em.createQuery(GET_DATA);

        List<Object[]> resultSet = query.getResultList();

        int currentId = 0;
        if (resultSet.size() > 0) {
            for (Object[] obj : resultSet) {
                if ((int) obj[0] != currentId) {
                    SalaryCollection collection = new SalaryCollection();
                    currentId = (int) obj[0];

                    Employee employee = new Employee();
                    employee.setId((int) obj[0]);
                    employee.setFirstName((String) obj[1]);
                    employee.setSecondName((String) obj[2]);

                    collection.setEmployee(employee);
                    collection.setOneDetail(populateDetailedSalary(obj));
                    fullCollection.add(collection);
                } else {
                    fullCollection.get(fullCollection.size() - 1).setOneDetail(populateDetailedSalary(obj));
                }
            }
        }

        return fullCollection;
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
    public boolean saveEmployee(Employee employee) {
        try {
            repository.save(employee);
        } catch (DataIntegrityViolationException ex) {
            return false;
        }

        return true;
    }

    @Override
    public boolean editEmployee(Employee employee) {
        try {
            Employee temp = repository.findById(employee.getId());
            temp.setSecondName(employee.getSecondName());
            temp.setFirstName(employee.getFirstName());
            temp.setExperience(employee.getExperience());
            temp.setCardId(employee.getCardId());

            repository.save(temp);
        } catch (DataIntegrityViolationException ex) {
            logger.warn("");
            return false;
        }

        return true;
    }

    @Override
    public void deleteEmployee(int id) {
        repository.deleteById(id);
    }

    private DetailedSalary populateDetailedSalary(Object[] obj) {
        DetailedSalary detail = new DetailedSalary();
        detail.setProductionDate((LocalDateTime) obj[3]);
        detail.setProductionReference((String) obj[4]);
        detail.setProduct((String) obj[5]);
        detail.setExperience((double) obj[6]);
        detail.setWorkedHours((double) obj[7]);
        detail.setSalary((double) obj[8]);

        return detail;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
