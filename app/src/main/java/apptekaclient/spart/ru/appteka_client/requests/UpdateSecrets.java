package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.List;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import retrofit2.Call;
import retrofit2.Response;


public class UpdateSecrets extends AsyncTask<Void, Void, Boolean> {
    private String authorization;
    private List<DrugModel> drugModels;

    public UpdateSecrets(String authorization, List<DrugModel> drugModels) {
        this.authorization = authorization;
        this.drugModels = drugModels;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Void> call = apiService.updateSecrets(authHeader, drugModels);
        try {
            Response<Void> response = call.execute();
            if (response.isSuccessful())
                return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
