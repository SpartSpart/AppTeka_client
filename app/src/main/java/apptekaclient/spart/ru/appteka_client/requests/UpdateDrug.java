package apptekaclient.spart.ru.appteka_client.requests;

import android.os.AsyncTask;
import android.util.Base64;

import java.io.IOException;

import apptekaclient.spart.ru.appteka_client.api.ApiConnection;
import apptekaclient.spart.ru.appteka_client.api.ApiService;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Pamela on 16.11.2019.
 */

public class UpdateDrug  extends AsyncTask<Void, Void, Boolean> {

        private String authorization;
        private Long id;
        private DrugModel drugModel;


    public UpdateDrug(String authorization, Long id, DrugModel drugModel) {
        this.authorization = authorization;
        this.id = id;
        this.drugModel = drugModel;
    }

        @Override
        protected Boolean doInBackground(Void... voids) {

            ApiService apiService = ApiConnection.getApiService();

            String authHeader = "Basic " + Base64.encodeToString(authorization.getBytes(), Base64.NO_WRAP);
            Call<Void> call = apiService.updateDrug(authHeader,id, drugModel);
            try {
                Response<Void> response = call.execute();
                if (response.isSuccessful())
                    return true ;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
