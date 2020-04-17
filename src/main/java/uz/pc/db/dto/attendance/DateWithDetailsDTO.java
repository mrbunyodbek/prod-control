package uz.pc.db.dto.attendance;

import java.util.ArrayList;
import java.util.List;

/**
 * DateWithDetailsDTO class will be used for collecting information
 * about particular working date.
 */
public class DateWithDetailsDTO {

    private int date;
    private List<DetailsDTO> details;

    public DateWithDetailsDTO(int date) {
        this.date = date;
        this.details = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<DetailsDTO> getDetails() {
        return details;
    }

    public void setDetails(DetailsDTO details) {
        this.details.add(details);
    }
}
