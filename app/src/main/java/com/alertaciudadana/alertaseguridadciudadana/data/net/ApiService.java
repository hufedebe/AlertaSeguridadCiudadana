package com.alertaciudadana.alertaseguridadciudadana.data.net;

import com.alertaciudadana.alertaseguridadciudadana.data.entity.Incidente;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.IncidentePost;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.IncidenteResponse;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.LoginPost;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.LoginResponse;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.RegistroPost;
import com.alertaciudadana.alertaseguridadciudadana.data.entity.RegistroResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {


    //@GET("incidentes")
    //Call<List<IncidenteResponse>> getIncidenteList ();
    @POST("users/")
    Call<RegistroResponse> postRegistro(@Body RegistroPost registroPost);

    @POST("auth/")
    Call<LoginResponse> postLogin(@Body LoginPost loginPost);

    @POST("incidentes/")
    Call<IncidenteResponse> postIncidente(@Body IncidentePost incidentePost);

    @GET("incidentes/")
    Call<List<Incidente>> getIncidenteList();


}
