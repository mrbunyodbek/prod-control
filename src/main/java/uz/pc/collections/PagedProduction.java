package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PagedProduction {

    private List<ProductionWithPerformers> pwp;
    private int totalPages;
    private int currentPage;
    private int countOfItems;

}
