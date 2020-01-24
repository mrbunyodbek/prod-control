package uz.pc.db.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pc.collections.EmployeeAndAttendance;
import uz.pc.db.dao.interfaces.AttendanceDAO;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.db.dao.interfaces.SettingsDAO;
import uz.pc.db.entities.Attendance;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Settings;
import uz.pc.db.repos.AttendanceRepository;
import uz.pc.services.SerialHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceDAOImpl implements AttendanceDAO {

    private AttendanceRepository repository;
    private EmployeeDAO employeeDAO;

    private Settings settings;

    private static Logger logger = LoggerFactory.getLogger(AttendanceDAOImpl.class);

    public AttendanceDAOImpl(AttendanceRepository repository, EmployeeDAO employeeDAO, SettingsDAO settingsDAO) {
        this.repository = repository;
        this.employeeDAO = employeeDAO;

        settings = settingsDAO.getSettings();
    }

    @Override
    public List<EmployeeAndAttendance> getAllAttendancesByEmployees(String month) {
        List<Employee> employees = employeeDAO.getAll();
        List<EmployeeAndAttendance> eaa = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeAndAttendance ea = new EmployeeAndAttendance();
            ea.setEmployee(employee);
            if (month == null) {
                ea.setAttendanceList(repository.findAllByEmployeeId(employee.getId()));
            } else {
                ea.setAttendanceList(repository.findAllByEmployeeIdAndMonth(employee.getId(), month));
            }

            if (ea.getAttendanceList().size() == 0) continue;
            eaa.add(ea);
        }

        return eaa;
    }

    @Override
    public EmployeeAndAttendance getAllAttendanceForOneEmployee(int employeeId) {
        EmployeeAndAttendance ea = new EmployeeAndAttendance();
        ea.setEmployee(employeeDAO.getById(employeeId));
        ea.setAttendanceList(repository.findAllByEmployeeId(ea.getEmployee().getId()));
        return ea;
    }

    public void registerEmployeeDeparture(String identification) {

        Attendance attendance = repository.findByCardIdAndClosedDayFalse(identification);
        if (attendance == null) return;
        attendance.setDepartureTime(LocalDateTime.now());
        attendance.setDepartureDifference(ChronoUnit.MINUTES.between(
                attendance.getArrivalTime(), attendance.getDepartureTime()
        ));
        attendance.setDepartureDate(LocalDate.now());
        attendance.setDepartureDifference(calculateDepartureDifference(LocalTime.now()));
        attendance.setClosedDay(true);

        repository.save(attendance);

    }

    public void registerEmployeeArrival(String identification) {
        Attendance attendance = new Attendance();

        attendance.setCardId(identification);

        attendance.setEmployeeId(employeeDAO.getIdByCardId(identification));

        attendance.setArrivalTime(LocalDateTime.now());
        attendance.setArrivalDifference(calculateArrivalDifference(LocalTime.now()));
        attendance.setWeekday(LocalDate.now().getDayOfWeek().name());
        attendance.setMonth(LocalDateTime.now().getMonth().name());
        attendance.setArrivalDate(LocalDate.now());
        attendance.setClosedDay(false);

        logger.warn("!!! Saving object to database started");
        repository.save(attendance);
        logger.warn("Saving object to database ended !!!");
    }

    private long calculateArrivalDifference(LocalTime now) {
        return ChronoUnit.MINUTES.between(
                now, settings.getStartOfDay()
        );
    }

    private long calculateDepartureDifference(LocalTime departure){
        assert settings.getEndOfDay() != null;
        return ChronoUnit.MINUTES.between(
                settings.getEndOfDay(), departure
        );
    }
}
