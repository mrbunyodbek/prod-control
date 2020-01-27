package uz.pc.db.dao.interfaces;

import org.springframework.data.domain.Page;
import uz.pc.collections.PagedProduction;
import uz.pc.collections.ProductionWithPerformers;
import uz.pc.collections.SavedProduction;
import uz.pc.collections.Statistics;

import java.util.List;

public interface ProductionDAO {

    PagedProduction getPaginatedProductions(int pageNo, int sizeOfThePage, boolean sortingDirection);

    List<ProductionWithPerformers> getAll();
    ProductionWithPerformers getById(int id);
    void saveProduction(SavedProduction collection);
    void deleteProduction(int id);

}
