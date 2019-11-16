package apptekaclient.spart.ru.appteka_client.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Pamela on 16.11.2019.
 */

public class AppointmentModel implements Serializable {

    @SerializedName("appointment")
    @Expose
    private String appointment;

    public AppointmentModel(String appointment) {
        this.appointment = appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }

    public String getAppointment() {
        return appointment;
    }
}