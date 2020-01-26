package uz.pc.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "db_attendance")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance extends UpdateBaseEntity {

    @Nullable
    @Column(name = "employee_id")
    private int employeeId;

    @Nullable
    @Column(name = "card_id")
    private String cardId;

    @Nullable
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Nullable
    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Nullable
    @Column(name = "arrival_day")
    private int arrivalDay;

    @Nullable
    @Column(name = "arrival_difference")
    private long arrivalDifference;

    @Nullable
    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Nullable
    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Nullable
    @Column(name = "departure_day")
    private int departureDay;

    @Nullable
    @Column(name = "departure_difference")
    private long departureDifference;

    @Nullable
    @Column(name = "worked_hours")
    private long workedHours;

    @Nullable
    @Column(name = "weekday")
    private String weekday;

    @Nullable
    @Column(name = "month")
    private String month;

    @Nullable
    @Column(name = "is_closed_day")
    private boolean closedDay = false;

}
