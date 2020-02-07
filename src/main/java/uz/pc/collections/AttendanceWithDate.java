package uz.pc.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * AttendanceWithDate class will be used for collecting information
 * about particular working date.
 */
public class AttendanceWithDate {

    private int date;
    private List<AttendanceDetails> details;

    public AttendanceWithDate(int date) {
        this.date = date;
        this.details = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public List<AttendanceDetails> getDetails() {
        return details;
    }

    public void setDetails(AttendanceDetails details) {
        this.details.add(details);
    }
}
