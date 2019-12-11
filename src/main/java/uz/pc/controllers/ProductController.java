package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.db.dao.interfaces.ProductDAO;
import uz.pc.db.entities.Product;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
public class ProductController {
    private ProductDAO dao;

    public ProductController(ProductDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Product> get(@PathVariable int id) {
        if (id == -1)
            return new ResponseEntity<>(new Product(), HttpStatus.OK);
        return new ResponseEntity<>(dao.getById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public HttpStatus save(@Valid @RequestBody Product product) {
        dao.saveProduct(product);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public HttpStatus edit(@Valid @RequestBody Product product) {
        dao.editProduct(product);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> delete(@PathVariable int id) {
        dao.deleteProduct(id);
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }
}
