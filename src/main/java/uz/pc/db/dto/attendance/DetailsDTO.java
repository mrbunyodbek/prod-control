package uz.pc.db.dto.attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetailsDTO {

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String workedHour;

}
