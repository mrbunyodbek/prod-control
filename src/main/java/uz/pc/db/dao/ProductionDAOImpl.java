package uz.pc.db.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.pc.collections.PagedProduction;
import uz.pc.collections.ProductionWithPerformers;
import uz.pc.collections.SavedProduction;
import uz.pc.collections.Statistics;
import uz.pc.db.dao.interfaces.PerformerDAO;
import uz.pc.db.dao.interfaces.ProductDAO;
import uz.pc.db.dao.interfaces.ProductionDAO;
import uz.pc.db.entities.Product;
import uz.pc.db.entities.Production;
import uz.pc.db.entities.Performer;
import uz.pc.db.repos.ProductionRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductionDAOImpl implements ProductionDAO {

    private ProductionRepository repository;
    private ProductDAO productDAO;
    private PerformerDAO performerDAO;

    public ProductionDAOImpl(ProductionRepository repository, ProductDAO productDAO, PerformerDAO performerDAO) {
        this.repository = repository;
        this.productDAO = productDAO;
        this.performerDAO = performerDAO;
    }

    @Override
    public PagedProduction getPaginatedProductions(int pageNo, int sizeOfThePage, boolean sortingDirection) {
        List<ProductionWithPerformers> collection = new ArrayList<>();
        Page<Production> pagedProductions;
        if (sortingDirection) {
            pagedProductions = repository.findAll(
                    PageRequest.of(pageNo, sizeOfThePage, Sort.by(Sort.Direction.DESC, "id"))
            );
        } else {
            pagedProductions = repository.findAll(
                    PageRequest.of(pageNo, sizeOfThePage, Sort.by(Sort.Direction.ASC, "id"))
            );
        }

        pagedProductions.forEach(production -> {
            ProductionWithPerformers one = new ProductionWithPerformers();
            one.setProduction(production);
            one.setPerformerRows(performerDAO.collectPerformers(production.getId()));
            one.setProduct(productDAO.getById(production.getProductId()));

            collection.add(one);
        });

        PagedProduction pagedProduction = new PagedProduction();
        pagedProduction.setPwp(collection);
        pagedProduction.setCurrentPage(pagedProductions.getNumber());
        pagedProduction.setCountOfItems(pagedProductions.getSize());
        pagedProduction.setTotalPages(pagedProductions.getTotalPages());
        return pagedProduction;
    }

    @Override
    public List<ProductionWithPerformers> getAll() {
        List<ProductionWithPerformers> collections = new ArrayList<>();

        for (Production production : repository.findAll()) {
            ProductionWithPerformers one = new ProductionWithPerformers();
            one.setProduction(production);
            one.setPerformerRows(performerDAO.collectPerformers(production.getId()));
            one.setProduct(productDAO.getById(production.getProductId()));

            collections.add(one);
        }
        return collections;
    }

    @Override
    public ProductionWithPerformers getById(int id) {
        Production production = repository.getById(id);
        ProductionWithPerformers one = new ProductionWithPerformers();
        one.setProduction(production);

        one.setPerformerRows(performerDAO.collectPerformers(production.getId()));
        one.setProduct(productDAO.getById(production.getProductId()));

        return one;
    }

    /**
     * Create new Production.
     * @param production Object collected from front-end.
     */
    @Override
    public void saveProduction(SavedProduction production) {
        production.getProduction().setDate(production.getProduction().getDate().withHour(15));
        production.getProduction().setDate(production.getProduction().getDate().withMinute(0));
        production.getProduction().setDate(production.getProduction().getDate().withSecond(0));
        production.getProduction().setDate(production.getProduction().getDate().withNano(0));

        Production savedProd = repository.save(production.getProduction());
        List<Performer> performers = production.getPerformers();

        for (int i = 0; i < performers.size(); i++) {
            performers.get(i).setProductionId(savedProd.getId());
            performers.get(i).setEmployeeId(production.getPerformers().get(i).getEmployeeId());

            performerDAO.savePerformer(performers.get(i));
        }
    }

    @Override
    public void deleteProduction(int id) {
        performerDAO.deletePerformer(id);
        repository.deleteById(id);
    }
}
