package uz.pc.db.dao.interfaces;

import uz.pc.db.dto.production.PerformerRow;
import uz.pc.db.entities.Performer;

import java.util.List;

public interface PerformerDAO {

    List<Performer> getAll();
    List<PerformerRow> collectPerformers(int productionId);
    Performer getById(int id);
    void savePerformer(Performer performer);
    void editPerformer(Performer performer);
    void deletePerformer(int id);

}
