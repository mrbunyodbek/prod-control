package uz.pc.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "db_production")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Production extends UpdateBaseEntity {

    @Column(name = "date")
    private LocalDateTime date;

    @Nullable
    @Column(name = "year")
    private int year;

    @Nullable
    @Column(name = "month")
    private String month;

    @Column(name = "reference_number")
    private String reference;

    @Column(name = "amount")
    private double amount;

    @Column(name = "cost")
    private double cost;

    @Column(name = "product_id")
    private int productId;
}
