package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import retrofit2.Call;
import retrofit2.Response;


public class AddDrug extends AsyncTask<Void, Void, Long> {
    private String authorization;
    private String description;
    private String login;
    private String password;

    public AddDrug(String authorization, String description, String login, String password) {
        this.authorization = authorization;
        this.description = description;
        this.login = login;
        this.password = password;
    }


    @Override
    protected Long doInBackground(Void... voids) {

//        ApiService apiService = ApiConnection.getApiService();
//
//        String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
//        Call<Long> call = apiService.addSecret(authHeader, new DrugModel());
//        try {
//            Response<Long> response = call.execute();
//            if (response.isSuccessful())
//                return response.body();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
