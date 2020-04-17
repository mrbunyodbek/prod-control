package uz.pc.db.dao.interfaces;

import uz.pc.db.dto.attendance.AttendanceDTO;

import java.util.List;

public interface AttendanceDAO {

    List<AttendanceDTO> collectAllAttendancesForMonth(String month);

}
