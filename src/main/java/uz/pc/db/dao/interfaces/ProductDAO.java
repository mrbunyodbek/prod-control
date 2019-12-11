package uz.pc.db.dao.interfaces;

import uz.pc.db.entities.Product;

import java.util.List;

public interface ProductDAO {

    List<Product>getAll();
    Product getById(int id);
    void saveProduct(Product product);
    void editProduct(Product product);
    void deleteProduct(int id);

}
