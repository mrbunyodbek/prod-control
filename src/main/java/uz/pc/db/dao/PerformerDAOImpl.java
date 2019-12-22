package uz.pc.db.dao;

import org.springframework.stereotype.Service;
import uz.pc.collections.PerformerRow;
import uz.pc.db.dao.interfaces.PerformerDAO;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Performer;
import uz.pc.db.repos.EmployeeRepository;
import uz.pc.db.repos.PerformerRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerformerDAOImpl implements PerformerDAO {

    private PerformerRepository repository;
    private EmployeeRepository employeeRepository;

    public PerformerDAOImpl(PerformerRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Performer> getAll() {
        return repository.findAll();
    }

    @Override
    public List<PerformerRow> collectPerformers(int productionId) {
        List<Performer> performers = repository.findAllByProductionId(productionId);

        List<PerformerRow> performerRows = new ArrayList<>();
        for (Performer performer : performers) {
            PerformerRow row = new PerformerRow();

            row.setId(performer.getId());
            row.setWorkedHours(performer.getWorkedHours());
            row.setSalary(performer.getSalary());
            row.setExperience(performer.getExperience());

            Employee emp = employeeRepository.findById(performer.getEmployeeId());
            row.setEmployee(emp);
            row.setFirstName(emp.getFirstName());
            row.setSecondName(emp.getSecondName());

            performerRows.add(row);
        }

        return performerRows;
    }

    @Override
    public Performer getById(int id) {
        return repository.getById(id);
    }

    @Override
    public void savePerformer(Performer performer) {
        repository.save(performer);
    }

    @Override
    public void editPerformer(Performer performer) {
        Performer temp = repository.getById(performer.getId());

        temp.setSalary(performer.getSalary());
        temp.setWorkedHours(performer.getWorkedHours());
        temp.setEmployeeId(performer.getEmployeeId());
        temp.setProductionId(performer.getProductionId());

        repository.save(temp);
    }

    @Override
    public void deletePerformer(int id) {
        repository.deleteAllByProductionId(id);
    }
}
