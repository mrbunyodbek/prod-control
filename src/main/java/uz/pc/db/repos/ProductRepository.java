package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pc.db.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);

    @Query("SELECT count(p.id) FROM Product p")
    int calculateCountOfProducts();

}
