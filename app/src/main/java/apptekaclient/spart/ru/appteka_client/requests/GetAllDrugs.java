package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import retrofit2.Call;
import retrofit2.Response;

public class GetAllDrugs extends AsyncTask<Void, Void, Collection<DrugModel>> {
    private String authorization;

    public GetAllDrugs(String authorization) {
        this.authorization = authorization;
    }

    @Override
    protected Collection<DrugModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Collection<DrugModel>> call = apiService.getAllDrugs(authHeader);
        try {
            Response<Collection<DrugModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
