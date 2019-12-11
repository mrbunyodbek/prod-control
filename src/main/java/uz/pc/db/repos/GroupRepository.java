package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {

    Group findById(int id);

}
