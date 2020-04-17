package uz.pc.db.dto.attendance;

import uz.pc.db.entities.Employee;

public class AttendanceDTO {

    private Employee employee;
    private DateWithDetailsDTO[] attendanceList;
    private int overallHours;

    public AttendanceDTO() {
        this.overallHours = 0;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public DateWithDetailsDTO[] getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(DateWithDetailsDTO[] attendanceList) {
        this.attendanceList = attendanceList;
    }

    public int getOverallHours() {
        return overallHours;
    }

    public void setOverallHours(int overallHours) {
        this.overallHours += overallHours;
    }
}
