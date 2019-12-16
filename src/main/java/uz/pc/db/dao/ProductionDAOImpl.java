package uz.pc.db.dao;

import org.springframework.stereotype.Service;
import uz.pc.collections.AllProduction;
import uz.pc.collections.SavedProduction;
import uz.pc.db.dao.interfaces.PerformerDAO;
import uz.pc.db.dao.interfaces.ProductDAO;
import uz.pc.db.dao.interfaces.ProductionDAO;
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
    public List<AllProduction> getAll() {
        List<AllProduction> collections = new ArrayList<>();

        for (Production production : repository.findAll()) {
            AllProduction one = new AllProduction();
            one.setProduction(production);
            one.setPerformerRows(performerDAO.collectPerformers(production.getId()));
            one.setProduct(productDAO.getById(production.getProductId()));

            collections.add(one);
        }
        return collections;
    }

    @Override
    public AllProduction getById(int id) {
        Production production = repository.getById(id);
        AllProduction one = new AllProduction();
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


        repository.save(production.getProduction());
        int id = repository.getByReference(production.getProduction().getReference()).getId();

        List<Performer> performers = production.getPerformers();
        for (int i = 0; i < performers.size(); i++) {
            performers.get(i).setProductionId(id);
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
