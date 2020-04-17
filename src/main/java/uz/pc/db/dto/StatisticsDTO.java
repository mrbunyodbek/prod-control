package uz.pc.db.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Hashtable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StatisticsDTO {

    Hashtable<String, Integer> costByProduct;
    Hashtable<String, Integer> amountByProduct;
    int productionCount;
    int productCount;
    int employeeCount;
    int overallCost;

}
