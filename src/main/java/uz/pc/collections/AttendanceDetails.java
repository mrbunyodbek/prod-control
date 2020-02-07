package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceDetails {

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String workedHour;

}
