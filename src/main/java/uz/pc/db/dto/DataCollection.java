package uz.pc.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.dto.production.ProductionWithPerformers;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Product;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DataCollection {

    private List<Employee> employees;
    private List<Product> products;
    private ProductionWithPerformers production;

}
