package uz.pc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pc.db.dao.interfaces.SettingsDAO;
import uz.pc.db.entities.Settings;

import java.time.LocalTime;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

    private SerialHandler serialHandler;
    private SettingsDAO settingsDAO;

    private Logger logger = LoggerFactory.getLogger(ApplicationStartupRunner.class);

    public ApplicationStartupRunner(SerialHandler serialHandler, SettingsDAO settingsDAO) {
        this.serialHandler = serialHandler;
        this.settingsDAO = settingsDAO;
    }

    @Override
    public void run(String... args) throws Exception {

        Settings settings = settingsDAO.getSettings();

        if (settings == null) {
            logger.info("Setting up default time to: 08:00:00 - 18:00:00");
            settings = new Settings();
            settings.setStartOfDay(LocalTime.of(8,0,0));
            settings.setEndOfDay(LocalTime.of(18,0,0));

            settingsDAO.saveSettings(settings);
        }

        serialHandler.start();
    }
}
