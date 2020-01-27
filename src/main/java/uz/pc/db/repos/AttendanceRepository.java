package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Attendance;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findAll();
    List<Attendance> findAllByEmployeeId(int employeeId);
    List<Attendance> findAllByArrivalDay(int day);
    List<Attendance> findAllByClosedDayFalse();

    List<Attendance> findAllByClosedDayTrueOrderByEmployeeIdDesc();
    List<Attendance> findAllByMonthOrderByEmployeeIdDesc(String month);

    List<Attendance> findAllByEmployeeIdAndMonth(int employeeId, String month);
    Attendance findByEmployeeIdAndClosedDayFalse(int employeeId);
    Attendance findByCardIdAndClosedDayFalse(String cardId);
    Attendance findByCardIdAndClosedDayTrue(String cardId);

}
