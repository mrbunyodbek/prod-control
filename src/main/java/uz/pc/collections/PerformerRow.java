package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Employee;

@Getter
@Setter
@NoArgsConstructor
public class PerformerRow {

    private int id;
    private Employee employee;
    private String firstName;
    private String secondName;
    private double experience;
    private double workedHours;
    private double salary;

}

