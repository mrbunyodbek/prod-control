package uz.pc.db.dto.salary;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class SalaryDetailDTO {

    private int productionId;
    private LocalDateTime productionDate;
    private String productionReference;
    private String product;
    private double experience;
    private double workedHours;
    private double salary;

}
