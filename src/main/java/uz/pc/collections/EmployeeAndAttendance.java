package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Employee;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeAndAttendance {

    Employee employee;
    AttendanceWithDate[] attendanceList;

}
