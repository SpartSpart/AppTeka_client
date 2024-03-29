package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Date;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import retrofit2.Call;
import retrofit2.Response;


public class AddDrug extends AsyncTask<Void, Void, Long> {
    private String authorization;
    private DrugModel drugModel;



    public AddDrug(String authorization, DrugModel drugModel) {
        this.authorization = authorization;
        this.drugModel = drugModel;
    }

    @Override
    protected Long doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Long> call = apiService.addDrug(authHeader, drugModel);
        try {
            Response<Long> response = call.execute();
            if (response.isSuccessful())
                return response.body();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
