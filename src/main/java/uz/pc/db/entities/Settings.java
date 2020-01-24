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
import java.time.LocalTime;

@Entity
@Table(name = "db_settings")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Settings extends UpdateBaseEntity {

    @Nullable
    @Column(name = "start_of_day")
    private LocalTime startOfDay;

    @Nullable
    @Column(name = "end_of_day")
    private LocalTime endOfDay;

}
