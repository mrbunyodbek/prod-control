package uz.pc.collections;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Production;
import uz.pc.db.entities.Performer;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SavedProduction {

    private Production production;
    private List<Performer> performers;

}
