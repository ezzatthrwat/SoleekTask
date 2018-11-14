package com.example.zizoj.soleektask.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zizoj.soleektask.R;
import com.example.zizoj.soleektask.Utils.CountriesListAdpter;
import com.example.zizoj.soleektask.Network.ApiClient;
import com.example.zizoj.soleektask.Network.ApiService;
import com.example.zizoj.soleektask.Network.model.Model;
import com.example.zizoj.soleektask.Network.model.Response;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class Home_Activity extends AppCompatActivity implements  CountriesListAdpter.OnCountryItemListener{

    private FirebaseAuth mFirebaseAuth;

    ApiService apiService;

    ArrayList<Response> CountriesList = new ArrayList<Response>();

    RecyclerView CountriesrecyclerView;
    CountriesListAdpter countriesListAdpter;

    ProgressBar progressBar;

    TextView countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        mFirebaseAuth = FirebaseAuth.getInstance();

        //Recyclerview
        initRecyclerview();

        initRetrofit();
        getAllCountriesData();

    }

    private void initRecyclerview() {

        CountriesrecyclerView = (RecyclerView) findViewById(R.id.CountriesRecyclerView);
        CountriesrecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CountriesrecyclerView.setLayoutManager(layoutManager);


        progressBar = findViewById(R.id.HomeActivityProgressBar);


        countryName = findViewById(R.id.CountryName);



    }

    public void logOutBtn(View view) {

        mFirebaseAuth.signOut();

        startActivity(new Intent(Home_Activity.this , Login_Activity.class));

        finish();

    }

    private void initRetrofit(){
        progressBar.setVisibility(View.VISIBLE);
        apiService = ApiClient.getClient(getApplication()).create(ApiService.class);
    }

    private void getAllCountriesData(){

        Call<Model> Call = apiService.methods1();

        Call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, retrofit2.Response<Model> response) {

                CountriesList.addAll(response.body().getResponse());
                countriesListAdpter = new CountriesListAdpter(CountriesList);
                CountriesrecyclerView.setAdapter(countriesListAdpter);

                progressBar.setVisibility(View.GONE);

                countriesListAdpter.setOnCountryItemListener(Home_Activity.this);

            }

            @Override
            public void onFailure(retrofit2.Call<Model> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(Home_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }


    @Override
    public void onCountryListItemSelected(int item) {

        if (CountriesList != null){
            String CountryString = CountriesList.get(item).getName();
            countryName.setText(CountryString);
            Toast.makeText(this,CountryString, Toast.LENGTH_SHORT).show();
        }
    }
}


