package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Product;

import java.util.HashMap;
import java.util.Hashtable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Statistics {

    Hashtable<String, Integer> costByProduct;
    int productionCount;
    int productCount;
    int employeeCount;

}
