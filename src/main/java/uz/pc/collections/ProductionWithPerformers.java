package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Product;
import uz.pc.db.entities.Production;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductionWithPerformers {

    private Production production;
    private Product product;
    private List<PerformerRow> performerRows;

}
