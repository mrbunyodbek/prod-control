package uz.pc.db.dao;

import org.springframework.stereotype.Service;
import uz.pc.db.dao.interfaces.GroupDAO;
import uz.pc.db.entities.Group;
import uz.pc.db.repos.GroupRepository;

import java.util.List;

@Service
public class GroupDAOImpl implements GroupDAO {

    private GroupRepository repository;

    public GroupDAOImpl(GroupRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Group> getAll() {
        return repository.findAll();
    }

    @Override
    public Group getById(int id) {
        return repository.findById(id);
    }

    @Override
    public void saveGroup(Group group) {
        repository.save(group);
    }

    @Override
    public void deleteGroup(int id) {
        repository.deleteById(id);
    }
}
