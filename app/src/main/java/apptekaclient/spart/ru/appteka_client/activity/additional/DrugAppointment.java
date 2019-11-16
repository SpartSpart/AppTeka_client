package apptekaclient.spart.ru.appteka_client.activity.additional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import apptekaclient.spart.ru.appteka_client.activity.DrugListActivity;
import apptekaclient.spart.ru.appteka_client.api.model.AppointmentModel;
import apptekaclient.spart.ru.appteka_client.api.model.TypeModel;
import apptekaclient.spart.ru.appteka_client.requests.GetAllAppointments;
import apptekaclient.spart.ru.appteka_client.requests.GetAllTypes;

/**
 * Created by Pamela on 16.11.2019.
 */

public class DrugAppointment {

    private String authorization = DrugListActivity.authorization;

    public ArrayList<String> getAppointments () throws ExecutionException, InterruptedException {
        ArrayList<String> appointments = new ArrayList<>();
        GetAllAppointments getAllAppointments = new GetAllAppointments(authorization);
        Collection<AppointmentModel> appointmentModels = getAllAppointments.execute().get();

        for (AppointmentModel appointmentModel:appointmentModels){
            appointments.add(appointmentModel.getAppointment());
        }

        return appointments;
    }


}
