package uz.pc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uz.pc.db.dto.SettingsAndMessage;
import uz.pc.db.dao.interfaces.SettingsDAO;
import uz.pc.db.entities.Settings;

import javax.validation.Valid;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    private SettingsDAO settingsDAO;

    public SettingsController(SettingsDAO settingsDAO) {
        this.settingsDAO = settingsDAO;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<Settings> getFiltered() {
        return new ResponseEntity<>(settingsDAO.getSettings(), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<SettingsAndMessage> getFiltered(@Valid @RequestBody Settings settings) {
        SettingsAndMessage sam = new SettingsAndMessage();
        String message = settingsDAO.saveSettings(settings);

        sam.setMessage(message);
        sam.setSettings(settingsDAO.getSettings());

        return new ResponseEntity<>(sam, HttpStatus.OK);
    }

}
