package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.AppointmentModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pamela on 16.11.2019.
 */

public class GetAllAppointments extends AsyncTask<Void, Void, Collection<AppointmentModel>> {
    private String authorization;

    public GetAllAppointments(String authorization) {
        this.authorization = authorization;
    }

    @Override
    protected Collection<AppointmentModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Collection<AppointmentModel>> call = apiService.getAllAppointments(authHeader);
        try {
            Response<Collection<AppointmentModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}