package uz.pc.db.dao.interfaces;

import uz.pc.db.entities.Settings;

public interface SettingsDAO {
    Settings getSettings();
    String saveSettings(Settings settings);
}
