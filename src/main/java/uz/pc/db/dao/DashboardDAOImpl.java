package uz.pc.db.dao;

import org.springframework.stereotype.Service;
import uz.pc.collections.Statistics;
import uz.pc.db.dao.interfaces.DashboardDAO;
import uz.pc.db.entities.Product;
import uz.pc.db.repos.EmployeeRepository;
import uz.pc.db.repos.ProductRepository;
import uz.pc.db.repos.ProductionRepository;

import java.util.Hashtable;
import java.util.List;

@Service
public class DashboardDAOImpl implements DashboardDAO {

    private ProductionRepository productionRepository;
    private ProductRepository productRepository;
    private EmployeeRepository employeeRepository;

    public DashboardDAOImpl(ProductionRepository productionRepository, ProductRepository productRepository, EmployeeRepository employeeRepository) {
        this.productionRepository = productionRepository;
        this.productRepository = productRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Statistics collectStatsForProduction() {
        List<Product> productList = productRepository.findAll();
        Statistics statistics = new Statistics();
        Hashtable<String, Integer> prods = new Hashtable<>();

        for (Product product : productList) {
            int cost = productionRepository.totalProductionByProduct(product.getId());
            prods.put(product.getName(), cost);
        }
        statistics.setCostByProduct(prods);
        statistics.setProductionCount(productionRepository.calculateCountOfProductions());
        statistics.setEmployeeCount(employeeRepository.calculateCountOfEmployees());
        statistics.setProductCount(productRepository.calculateCountOfProducts());

        return statistics;
    }
}
