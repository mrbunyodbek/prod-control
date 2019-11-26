package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findById(int id);

}
