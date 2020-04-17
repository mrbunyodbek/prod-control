package uz.pc.db.dto.production;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.dto.production.ProductionWithPerformers;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductionPageDTO {

    private List<ProductionWithPerformers> pwp;
    private int totalPages;
    private int currentPage;
    private int countOfItems;

}
