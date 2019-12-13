package uz.pc.db.dao.interfaces;

import uz.pc.collections.AllProduction;
import uz.pc.collections.SavedProduction;
import uz.pc.db.entities.Production;

import java.util.List;

public interface ProductionDAO {

    List<AllProduction> getAll();
    AllProduction getById(int id);
    void saveProduction(SavedProduction collection);
    void deleteProduction(int id);

}
