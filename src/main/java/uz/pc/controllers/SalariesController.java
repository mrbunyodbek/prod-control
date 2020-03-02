package uz.pc.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.collections.Filter;
import uz.pc.collections.SalaryCollection;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.services.XLSHandlerService;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return new ResponseEntity<>(dao.getSalariesInformation(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-to-file", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> saveToFile(@Valid @RequestBody Filter filter) {
        List<SalaryCollection> collection = dao.getSalariesInformation(filter);
        XLSHandlerService xls = new XLSHandlerService(collection, filter.getStart(), filter.getEnd());

        xls.createXls(true);

        File file2Upload = new File("segmented.xlsx");
        Path path = Paths.get(file2Upload.getAbsolutePath());

        ByteArrayResource res = null;
        try {
            res = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=segmented.xlsx");
        System.out.println("reached");
        System.out.println(res);
        return new ResponseEntity<>(res,
                header, HttpStatus.OK);
    }

    @RequestMapping(value = "/save-overall", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> saveOverallToFile(@Valid @RequestBody Filter filter) {
        List<SalaryCollection> collection = dao.getSalariesInformation(filter);
        XLSHandlerService xls = new XLSHandlerService(collection, filter.getStart(), filter.getEnd());

        xls.createXls(false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        File file2Upload = new File("overall.xlsx");
        Path path = Paths.get(file2Upload.getAbsolutePath());

        ByteArrayResource res = null;
        try {
            res = new ByteArrayResource(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "force-download"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=overall.xlsx");
        System.out.println("reached");
        System.out.println(res);
        return new ResponseEntity<>(res,
                header, HttpStatus.OK);
    }

}
