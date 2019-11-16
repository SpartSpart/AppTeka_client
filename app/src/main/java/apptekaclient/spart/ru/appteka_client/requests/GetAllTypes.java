package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;
import java.util.Collection;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.api.model.TypeModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pamela on 16.11.2019.
 */

public class GetAllTypes extends AsyncTask<Void, Void, Collection<TypeModel>> {
    private String authorization;

    public GetAllTypes(String authorization) {
        this.authorization = authorization;
    }

    @Override
    protected Collection<TypeModel> doInBackground(Void... voids) {

        ApiService apiService = ApiConnection.getApiService();

        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
        Call<Collection<TypeModel>> call = apiService.getAllTypes(authHeader);
        try {
            Response<Collection<TypeModel>> response = call.execute();
            if (response.isSuccessful())
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
