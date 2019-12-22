package uz.pc.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "db_performers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Performer extends UpdateBaseEntity {

    @Nullable
    @Column(name = "experience")
    private double experience;

    @Nullable
    @Column(name = "worked_hours")
    private double workedHours;

    @Nullable
    @Column(name = "salary")
    private double salary;

    @Nullable
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "production_id")
    private int productionId;

}
