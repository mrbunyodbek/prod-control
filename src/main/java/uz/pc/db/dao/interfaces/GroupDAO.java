package uz.pc.db.dao.interfaces;

import uz.pc.db.entities.Group;

import java.util.List;

public interface GroupDAO {

    List<Group> getAll();
    Group getById(int id);
    void saveGroup(Group group);
    void deleteGroup(int id);

}
