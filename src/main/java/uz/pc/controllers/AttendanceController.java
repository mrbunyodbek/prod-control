package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.pc.db.dto.attendance.AttendanceDTO;
import uz.pc.db.dao.interfaces.AttendanceDAO;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private AttendanceDAO attendanceDAO;

    public AttendanceController(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<AttendanceDTO>> getAll() {
        return new ResponseEntity<>(attendanceDAO.collectAllAttendancesForMonth(null), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{month}", method = RequestMethod.GET)
    public ResponseEntity<List<AttendanceDTO>> getAllByMonth(@PathVariable String month) {
        return new ResponseEntity<>(attendanceDAO.collectAllAttendancesForMonth(month.toUpperCase()), HttpStatus.OK);
    }
}
