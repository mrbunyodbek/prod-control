package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Product;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DetailedSalary {

    private int productionId;
    private LocalDateTime productionDate;
    private String productionReference;
    private Product product;
    private double workedHours;
    private double salary;

}
