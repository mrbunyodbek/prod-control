package uz.pc.db.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import uz.pc.db.dto.attendance.DetailsDTO;
import uz.pc.db.dto.attendance.DateWithDetailsDTO;
import uz.pc.db.dto.attendance.AttendanceDTO;
import uz.pc.db.dao.interfaces.AttendanceDAO;
import uz.pc.db.dao.interfaces.EmployeeDAO;
import uz.pc.db.dao.interfaces.SettingsDAO;
import uz.pc.db.entities.Attendance;
import uz.pc.db.entities.Employee;
import uz.pc.db.entities.Settings;
import uz.pc.db.repos.AttendanceRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AttendanceDAOImpl implements AttendanceDAO {

    private AttendanceRepository repository;
    private EmployeeDAO employeeDAO;
    private Settings settings;

    private EntityManager em;

    public AttendanceDAOImpl(AttendanceRepository repository, EmployeeDAO employeeDAO, SettingsDAO settingsDAO) {
        this.repository = repository;
        this.employeeDAO = employeeDAO;
        settings = settingsDAO.getSettings();
    }

    @Override
    @Transactional
    public List<AttendanceDTO> collectAllAttendancesForMonth(String month) {
        List<AttendanceDTO> attendanceList = new ArrayList<>();
        if (month == null) month = LocalDateTime.now().getMonth().name();

        String QUERY =
                "SELECT emp.id, emp.firstName, emp.secondName, att.arrivalDay, att.arrivalTime, att.workedHours, att.departureTime " +
                        "FROM Employee emp " +
                        "INNER JOIN Attendance att on emp.id = att.employeeId " +
                        "WHERE att.month = '" + month +"' AND att.workedHours > 1 " +
                        "ORDER BY emp.id, att.arrivalDay ASC";

        Query query = em.createQuery(QUERY);
        List<Object[]> resultSet = query.getResultList();

        int currentId = 0;
        if (resultSet.size() > 0) {
            for (Object[] obj : resultSet) {
                if ((int) obj[0] != currentId) {
                    currentId = (int) obj[0];
                    AttendanceDTO oneAttendance = new AttendanceDTO();
                    Employee employee = new Employee();
                    employee.setId((int) obj[0]);
                    employee.setFirstName((String) obj[1]);
                    employee.setSecondName((String) obj[2]);

                    oneAttendance.setEmployee(employee);

                    DateWithDetailsDTO[] atWithDate = new DateWithDetailsDTO[this.parseMonthNameToCountOfDays(month)];
                    for (int i = 0; i < atWithDate.length; i++) {
                        atWithDate[i] = new DateWithDetailsDTO(i+1);
                    }
                    oneAttendance.setAttendanceList(atWithDate);

                    attendanceList.add(oneAttendance);
                }
                attendanceList.get(attendanceList.size() - 1).getAttendanceList()[(int) obj[3] - 1].setDetails(makeOneAttendanceDetail(obj));
            }
        }
        return attendanceList;
    }

    public boolean registerEmployeeDeparture(String identification) {

        try {
            Attendance attendance = repository.findByCardIdAndClosedDayFalse(identification);
            if (attendance == null) return false;
            attendance.setDepartureTime(LocalDateTime.now());
            attendance.setDepartureDifference(ChronoUnit.MINUTES.between(
                    attendance.getArrivalTime(), attendance.getDepartureTime()
            ));
            attendance.setDepartureDate(LocalDate.now());

            attendance.setWorkedHours(
                    calculateWorkedHours(attendance.getArrivalTime(), attendance.getDepartureTime())
            );

            attendance.setDepartureDay(LocalDate.now().getDayOfMonth());
            attendance.setDepartureDifference(calculateDepartureDifference(LocalTime.now()));
            attendance.setClosedDay(true);

            repository.save(attendance);

            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            log.warn("Two arrivals were registered. Please, check database entries.");
        }

        return false;

    }

    public boolean registerEmployeeArrival(String identification) {
        Employee employee = employeeDAO.getIdByCardId(identification);
        if (employee != null) {
            Attendance attendance = new Attendance();

            Attendance checker = repository.findByCardIdAndClosedDayFalse(identification);
            if (checker != null) {
                return true;
            }

            attendance.setCardId(identification);

            attendance.setEmployeeId(employee.getId());

            attendance.setArrivalTime(LocalDateTime.now());
            attendance.setArrivalDifference(calculateArrivalDifference(LocalTime.now()));
            attendance.setWeekday(LocalDate.now().getDayOfWeek().name());
            attendance.setMonth(LocalDateTime.now().getMonth().name());
            attendance.setArrivalDay(LocalDate.now().getDayOfMonth());
            attendance.setArrivalDate(LocalDate.now());
            attendance.setClosedDay(false);

            repository.save(attendance);

            return true;
        }

        return false;
    }

    private DetailsDTO makeOneAttendanceDetail(Object[] obj) {
        DetailsDTO oneDetail = new DetailsDTO();
        oneDetail.setArrivalTime((LocalDateTime) obj[4]);
        oneDetail.setDepartureTime((LocalDateTime) obj[6]);
        oneDetail.setWorkedHour(generateWorkedHourString((long) obj[5]));

        return oneDetail;
    }

    private String generateWorkedHourString(long wh) {
        int minutes = (int) wh % 60;
        int hours = (int) wh / 60;

        String strHours = "";
        String strMinutes = "";

        if (hours < 10) strHours = "0" + hours;
        else strHours = "" + hours;

        if (minutes < 10) strMinutes = "0" + minutes;
        else strMinutes = "" + minutes;

        return strHours + ":" + strMinutes;
    }

    private long calculateArrivalDifference(LocalTime now) {
        return ChronoUnit.MINUTES.between(
                now, settings.getStartOfDay()
        );
    }

    private long calculateWorkedHours(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.MINUTES.between(
                start, end
        );
    }

    private long calculateDepartureDifference(LocalTime departure){
        assert settings.getEndOfDay() != null;
        return ChronoUnit.MINUTES.between(
                settings.getEndOfDay(), departure
        );
    }

    private int parseMonthNameToCountOfDays(String month) {
        int returningValue = 0;

        switch (month) {
            case "JANUARY":
                returningValue = this.returnValue(1);
                break;
            case "FEBRUARY":
                returningValue = this.returnValue(2);
                break;
            case "MARCH":
                returningValue = this.returnValue(3);
                break;
            case "APRIL":
                returningValue = this.returnValue(4);
                break;
            case "MAY":
                returningValue = this.returnValue(5);
                break;
            case "JUNE":
                returningValue = this.returnValue(6);
                break;
            case "JULY":
                returningValue = this.returnValue(7);
                break;
            case "AUGUST":
                returningValue = this.returnValue(8);
                break;
            case "SEPTEMBER":
                returningValue = this.returnValue(9);
                break;
            case "OCTOBER":
                returningValue = this.returnValue(10);
                break;
            case "NOVEMBER":
                returningValue = this.returnValue(11);
                break;
            case "DECEMBER":
                returningValue = this.returnValue(12);
                break;
            default:
                returningValue = 30;
        }

        return returningValue;
    }

    private int returnValue(int monthNumber) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return LocalDate.of(currentDateTime.getYear(), monthNumber, 1).lengthOfMonth();
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }
}
