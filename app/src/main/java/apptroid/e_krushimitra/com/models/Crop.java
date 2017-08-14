package apptroid.e_krushimitra.com.models;

/**
 * Created by Apptroid 01 on 15-03-2017.
 */

public class Crop {
    String state, city, time, date, less,unit,quantity;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getState() {
        return state;

    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLess() {
        return less;
    }

    public void setLess(String less) {
        this.less = less;
    }
}
