package uz.pc.db.dao.interfaces;

import uz.pc.collections.EmployeeAndAttendance;
import uz.pc.db.entities.Attendance;

import java.util.List;

public interface AttendanceDAO {

    List<EmployeeAndAttendance> getAllAttendancesByEmployees(String month);
    EmployeeAndAttendance getAllAttendanceForOneEmployee(int employeeId);

}
