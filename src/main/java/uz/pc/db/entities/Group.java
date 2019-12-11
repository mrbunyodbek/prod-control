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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "db_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group extends UpdateBaseEntity {

    @NotNull
    @Column(name = "name")
    private String name;

    @Nullable
    @Column(name = "description")
    private String description;

}
