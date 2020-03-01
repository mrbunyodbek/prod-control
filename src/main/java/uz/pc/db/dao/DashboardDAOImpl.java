package uz.pc.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AopInvocationException;
import org.springframework.stereotype.Service;
import uz.pc.collections.Statistics;
import uz.pc.db.dao.interfaces.DashboardDAO;
import uz.pc.db.entities.Product;
import uz.pc.db.repos.EmployeeRepository;
import uz.pc.db.repos.ProductRepository;
import uz.pc.db.repos.ProductionRepository;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.List;

@Service
public class DashboardDAOImpl implements DashboardDAO {

    private ProductionRepository productionRepository;
    private ProductRepository productRepository;
    private EmployeeRepository employeeRepository;

    private Logger logger = LoggerFactory.getLogger(DashboardDAOImpl.class);

    public DashboardDAOImpl(ProductionRepository productionRepository, ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.productionRepository = productionRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Statistics collectStatsForProduction() {
        LocalDateTime thisTime = LocalDateTime.now();

        List<Product> productList = productRepository.findAll();
        Statistics statistics = new Statistics();
        Hashtable<String, Integer> prods = new Hashtable<>();
        Hashtable<String, Integer> amounts = new Hashtable<>();

        for (Product product : productList) {
            try {
                int cost = productionRepository.totalProductionByProduct(product.getId(), thisTime.getMonth().name(), thisTime.getYear());
                int amount = productionRepository.totalAmountOfProductionByProduct(product.getId(), thisTime.getMonth().name(), thisTime.getYear());

                prods.put(product.getName(), cost);
                amounts.put(product.getName(), amount);
            } catch (AopInvocationException ex) {
                logger.warn("AopInvocationException. Couldn't found data for " + product.getName());
            }
        }
        statistics.setCostByProduct(prods);
        statistics.setAmountByProduct(amounts);
        statistics.setProductionCount(productionRepository.calculateCountOfProductions(thisTime.getMonth().name(), thisTime.getYear()));
        statistics.setEmployeeCount(employeeRepository.calculateCountOfEmployees());
        statistics.setProductCount(productRepository.calculateCountOfProducts());
        try {
            statistics.setOverallCost(productionRepository.calculateFullCostOfProductions(thisTime.getMonth().name(), thisTime.getYear()));
        } catch (AopInvocationException e) {
            statistics.setOverallCost(0);
        }

        return statistics;
    }
}
