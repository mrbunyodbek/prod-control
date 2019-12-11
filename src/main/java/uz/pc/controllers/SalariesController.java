package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.collections.Filter;
import uz.pc.collections.SalaryCollection;
import uz.pc.db.dao.interfaces.EmployeeDAO;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/salaries")
@CrossOrigin("http://localhost:3000")
public class SalariesController {

    private EmployeeDAO dao;

    public SalariesController(EmployeeDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<List<SalaryCollection>> getFiltered(@Valid @RequestBody Filter filter) {
        return new ResponseEntity<>(dao.getAllForSalaries(filter), HttpStatus.OK);
    }

}
