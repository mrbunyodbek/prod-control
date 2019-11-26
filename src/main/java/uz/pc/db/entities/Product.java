package uz.pc.db.entities;

import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.base.UpdateBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "db_products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends UpdateBaseEntity {

    @Column(name = "product_name")
    private String name;

    @Nullable
    @Column(name = "product_desc")
    private String description;

    @Column(name = "product_rate")
    private Double rate;

}
