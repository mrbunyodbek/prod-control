package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Filter {

    LocalDateTime start;
    LocalDateTime end;

}
