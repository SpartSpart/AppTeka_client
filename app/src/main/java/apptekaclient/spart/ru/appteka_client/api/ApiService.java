package apptekaclient.spart.ru.appteka_client.api;

import java.util.Collection;
import java.util.List;

import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @POST("user/add/")
    Call<Void> addUser(@Body UserModel userModel);

    //@FormUrlEncoded
    @GET("drugs/")
    Call<Collection<DrugModel>> getAllDrugs(@Header("Authorization") String authHeader);

//////
    @POST("secrets/")
    Call<Long> addDrug(@Header("Authorization") String authHeader, @Body DrugModel drugModel);

///// ПУТИ ПРОВЕРЬ
///
    @PUT("secrets/")
    Call<Void> updateSecrets(@Header("Authorization") String authHeader, @Body List<DrugModel> drugModels);

    @POST("login/")
    Call<Void> loginUser(@Header("Authorization") String authHeader);
}

