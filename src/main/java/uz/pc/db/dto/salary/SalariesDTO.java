package uz.pc.db.dto.salary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Employee;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SalariesDTO {

    private Employee employee;
    private List<SalaryDetailDTO> details = new ArrayList<>();
    private double overallSalary = 0;

    public void setOneDetail(SalaryDetailDTO detail) {
        this.details.add(detail);
        this.overallSalary += detail.getSalary();
    }

    public void calcOverallSalary(double salary) {
        this.overallSalary += salary;
    }
}
