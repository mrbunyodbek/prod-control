package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class SalaryDetail {

    private int productionId;
    private Date productionDate;
    private String productionReference;
    private double workedHours;
    private double salary;

}
