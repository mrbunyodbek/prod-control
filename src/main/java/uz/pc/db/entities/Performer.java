package uz.pc.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "db_performers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Performer extends UpdateBaseEntity {

    @Column(name = "worked_hours")
    private double workedHours;

    @Column(name = "salary")
    private double salary;

    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "production_id")
    private int productionId;

}
