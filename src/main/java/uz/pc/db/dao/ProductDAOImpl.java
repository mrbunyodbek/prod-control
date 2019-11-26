package uz.pc.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pc.db.repos.ProductRepository;
import uz.pc.db.dao.interfaces.ProductDAO;
import uz.pc.db.entities.Product;

import java.util.List;

@Service
public class ProductDAOImpl implements ProductDAO {

    private ProductRepository repository;

    @Autowired
    public ProductDAOImpl(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(int id) {
        return repository.findById(id);
    }

    public void saveProduct(Product product) {
        repository.save(product);
    }

    public void editProduct(Product product) {
        Product temp = repository.findById(product.getId());

        temp.setDescription(product.getDescription());
        temp.setName(product.getName());
        temp.setRate(product.getRate());

        repository.save(temp);

    }

    public void deleteProduct(int id) {
        repository.deleteById(id);
    }
}
