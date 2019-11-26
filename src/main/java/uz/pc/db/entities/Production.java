package uz.pc.db.entities;

import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "production")
public class Production extends UpdateBaseEntity {

    private Date date;
    private String reference;
    private Product product;
    private Double amount;
    private Double sum;
    

}
