package uz.pc.db.dao.interfaces;

import uz.pc.db.dto.production.ProductionPageDTO;
import uz.pc.db.dto.production.ProductionWithPerformers;
import uz.pc.db.dto.ProductionDTO;

import java.util.List;

public interface ProductionDAO {

    ProductionPageDTO getPaginatedProductions(int pageNo, int sizeOfThePage, boolean sortingDirection);

    List<ProductionWithPerformers> getAll();
    ProductionWithPerformers getById(int id);
    void saveProduction(ProductionDTO collection);
    void deleteProduction(int id);

}
