package uz.pc.db.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Production;
import uz.pc.db.entities.Performer;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductionDTO {

    private Production production;
    private List<Performer> performers;

}
