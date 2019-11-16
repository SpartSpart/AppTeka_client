package apptekaclient.spart.ru.appteka_client.api;

import java.util.Collection;
import java.util.List;

import apptekaclient.spart.ru.appteka_client.api.model.AppointmentModel;
import apptekaclient.spart.ru.appteka_client.api.model.DrugModel;
import apptekaclient.spart.ru.appteka_client.api.model.TypeModel;
import apptekaclient.spart.ru.appteka_client.api.model.UserModel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @POST("login/")
    Call<Void> loginUser(@Header("Authorization") String authHeader);

    @POST("user/add/")
    Call<Void> addUser(@Body UserModel userModel);

    //@FormUrlEncoded
    @GET("drugs/")
    Call<Collection<DrugModel>> getAllDrugs(@Header("Authorization") String authHeader);


    @POST("drug/")
    Call<Long> addDrug(@Header("Authorization") String authHeader, @Body DrugModel drugModel);


    @PUT("drug/{id}")
    Call<Void> updateDrug(@Header("Authorization") String authHeader, @Path("id") long id, @Body DrugModel drugModel);

    @DELETE("drug/{id}")
    Call<Void> deleteDrug(@Header("Authorization") String authHeader, @Path("id") long id);

    @GET("types/")
    Call<Collection<TypeModel>> getAllTypes(@Header("Authorization") String authHeader);

    @GET("appointments/")
    Call<Collection<AppointmentModel>> getAllAppointments(@Header("Authorization") String authHeader);



}

