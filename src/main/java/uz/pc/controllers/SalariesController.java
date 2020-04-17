package uz.pc.controllers;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pc.db.dto.Filter;
import uz.pc.db.dto.salary.SalariesDTO;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.services.XLSHandlerService;

import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/salaries")
@CrossOrigin("http://localhost:3000")
public class SalariesController {

    private EmployeeDAO dao;

    public SalariesController(EmployeeDAO dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ResponseEntity<List<SalariesDTO>> getFiltered(@Valid @RequestBody Filter filter) {
        return new ResponseEntity<>(dao.getSalariesInformation(filter), HttpStatus.OK);
    }

    @RequestMapping(value = "/save-to-file", method = RequestMethod.POST)
    public ResponseEntity<ByteArrayResource> saveToFile(@Valid @RequestBody Filter filter) {
        List<SalariesDTO> collection = dao.getSalariesInformation(filter);
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
        header.setContentType(MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=segmented.xlsx");
        System.out.println("reached");
        System.out.println(res);
        return new ResponseEntity<>(res,
                header, HttpStatus.OK);
    }

    @RequestMapping(value = "/save-overall", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> saveOverallToFile(@Valid @RequestBody Filter filter) throws IOException {
        List<SalariesDTO> collection = dao.getSalariesInformation(filter);
        XLSHandlerService xls = new XLSHandlerService(collection, filter.getStart(), filter.getEnd());

        xls.createXls(false);

        FileOutputStream fos = new FileOutputStream("compressed-overall.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File("overall.xlsx");
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();

        File file2Upload = new File("compressed-overall.zip");
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file2Upload));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.valueOf("application/zip"));
        header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=compressed-overall.zip");

        return ResponseEntity.ok()
                .headers(header)
                .body(resource);
    }

}
