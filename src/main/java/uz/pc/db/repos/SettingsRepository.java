package uz.pc.db.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pc.db.entities.Settings;

public interface SettingsRepository extends JpaRepository<Settings, Integer> {

    Settings getById(int id);

}
