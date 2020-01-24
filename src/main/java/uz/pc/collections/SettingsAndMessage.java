package uz.pc.collections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pc.db.entities.Settings;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SettingsAndMessage {

    private Settings settings;
    private String message;

}
