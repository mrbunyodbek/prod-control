package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Attendance;
import uz.pc.db.entities.Employee;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeAndAttendance {

    Employee employee;
    List<Attendance> attendanceList;

}
