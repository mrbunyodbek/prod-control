package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Employee;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SalaryCollection {

    private Employee employee;
    private List<DetailedSalary> details = new ArrayList<>();
    private double overallSalary = 0;

    public void setOneDetail(DetailedSalary detail) {
        this.details.add(detail);
    }

    public void calcOverallSalary(double salary) {
        this.overallSalary += salary;
    }
}
