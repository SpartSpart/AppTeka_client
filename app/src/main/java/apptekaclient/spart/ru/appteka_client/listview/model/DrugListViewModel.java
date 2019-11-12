package apptekaclient.spart.ru.appteka_client.listview.model;

import java.io.Serializable;
import java.util.Date;

public class DrugListViewModel implements Serializable {
    private long id;
    private String name;
    private String appointment;
    private String count;
    private String type;
    private Date date;


    public DrugListViewModel(long id, String name, String type, String count, String appointment, Date date) {
        this.id = id;
        this.name = name;
        this.appointment = appointment;
        this.count = count;
        this.type = type;
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
