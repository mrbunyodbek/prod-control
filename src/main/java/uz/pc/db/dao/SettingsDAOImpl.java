package uz.pc.db.dao;

import org.springframework.stereotype.Service;
import uz.pc.db.dao.interfaces.SettingsDAO;
import uz.pc.db.entities.Settings;
import uz.pc.db.repos.SettingsRepository;

@Service
public class SettingsDAOImpl implements SettingsDAO {

    private SettingsRepository repository;

    public SettingsDAOImpl(SettingsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Settings getSettings() {
        return repository.getById(1);
    }

    @Override
    public String saveSettings(Settings settings) {
        Settings temp = repository.getById(1);
        temp.setStartOfDay(settings.getStartOfDay());
        temp.setEndOfDay(settings.getEndOfDay());

        repository.save(temp);

        return "Time has been saved";
    }
}
