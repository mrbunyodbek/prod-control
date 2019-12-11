package uz.pc.db.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "db_production")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Production extends UpdateBaseEntity {

    @Column(name = "date")
    private Date date;

    @Column(name = "reference_number")
    private String reference;

    @Column(name = "amount")
    private double amount;

    @Column(name = "cost")
    private double cost;

    @Column(name = "product_id")
    private int productId;
}
