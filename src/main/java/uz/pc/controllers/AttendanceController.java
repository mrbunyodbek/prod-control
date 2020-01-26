package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.pc.collections.EmployeeAndAttendance;
import uz.pc.db.dao.interfaces.AttendanceDAO;
import uz.pc.db.entities.Employee;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private AttendanceDAO attendanceDAO;

    public AttendanceController(AttendanceDAO attendanceDAO) {
        this.attendanceDAO = attendanceDAO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeAndAttendance>> getAll() {
        return new ResponseEntity<>(attendanceDAO.getAllAttendancesByEmployees(null), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/{month}", method = RequestMethod.GET)
    public ResponseEntity<List<EmployeeAndAttendance>> getAllByMonth(@PathVariable String month) {
        return new ResponseEntity<>(attendanceDAO.getAllAttendancesByEmployees(month.toUpperCase()), HttpStatus.OK);
    }

    @RequestMapping(value = "/get/by-employee/{employeeId}", method = RequestMethod.GET)
    public ResponseEntity<EmployeeAndAttendance> getAllByEmployeeAndMonth(
            @PathVariable int employeeId) {
        return new ResponseEntity<>(attendanceDAO.getAllAttendanceForOneEmployee(employeeId), HttpStatus.OK);
    }

}
