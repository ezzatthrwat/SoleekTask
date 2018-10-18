package com.example.zizoj.soleektask.Network;

import com.example.zizoj.soleektask.Network.model.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("v1/Country/getCountries")
    Call<Model> methods1();


}
