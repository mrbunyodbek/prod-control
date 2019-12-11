package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.collections.DataCollection;
import uz.pc.collections.AllProduction;
import uz.pc.collections.SavedProduction;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.db.dao.interfaces.ProductDAO;
import uz.pc.db.dao.interfaces.ProductionDAO;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Product;
import uz.pc.db.entities.Production;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/production")
@CrossOrigin("http://localhost:3000")
public class ProductionController {

    private ProductionDAO dao;
    private ProductDAO productDAO;
    private EmployeeDAO employeeDAO;

    public ProductionController(ProductionDAO dao, ProductDAO productDAO, EmployeeDAO employeeDAO) {
        this.dao = dao;
        this.productDAO = productDAO;
        this.employeeDAO = employeeDAO;
    }

    /**
     * Get all Production instances from DB.
     *
     * @return JSON list of all Productions.
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<AllProduction>> getAll() {
        return new ResponseEntity<>(dao.getAll(), HttpStatus.OK);
    }

    /**
     * Collecting all data of Products and Employees.
     *
     * @return JSON of all two instances
     */
    @RequestMapping(value = "/collect-data", method = RequestMethod.GET)
    public ResponseEntity<DataCollection> collectInitialData() {
        return new ResponseEntity<>(collect(), HttpStatus.OK);
    }

    /**
     * Register and save new instance of the Production
     *
     * @param collection JSON data from front-end.
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public HttpStatus saveNewProduction(@Valid @RequestBody SavedProduction collection) {
        dao.saveProduction(collection);
        return HttpStatus.OK;
    }

    public void updateProduction(Production production) {

    }

    /**
     * Delete Production by its id.
     *
     * @param id ID variable of the Production.
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public HttpStatus deleteProduction(@PathVariable int id) {
        dao.deleteProduction(id);
        return HttpStatus.OK;
    }

    /**
     * Method creates wrapper which contains lists with two instances,
     * like Product and Employee. This wrapper could be used for sending
     * multiple objects in one response. Datatype of this wrapper is simple JSON.
     *
     * @return Instance collection.
     */
    private DataCollection collect() {
        DataCollection collection = new DataCollection();

        List<Product> prods = productDAO.getAll();
        List<Employee> emps = employeeDAO.getAll();

        collection.setEmployees(emps);
        collection.setProducts(prods);

        return collection;
    }

}
