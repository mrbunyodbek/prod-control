package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * AttendanceWithDate class will be used for collecting information
 * about particular working date.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceWithDate {

    private int date;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private int workedHour;

}
