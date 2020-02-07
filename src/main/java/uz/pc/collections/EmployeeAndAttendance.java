package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Employee;

public class EmployeeAndAttendance {

    private Employee employee;
    private AttendanceWithDate[] attendanceList;
    private int overallHours;

    public EmployeeAndAttendance() {
        this.overallHours = 0;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AttendanceWithDate[] getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(AttendanceWithDate[] attendanceList) {
        this.attendanceList = attendanceList;
    }

    public int getOverallHours() {
        return overallHours;
    }

    public void setOverallHours(int overallHours) {
        this.overallHours += overallHours;
    }
}
