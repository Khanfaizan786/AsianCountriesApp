package com.example.asiancountries.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.asiancountries.database.CountryDao;
import com.example.asiancountries.database.CountryDatabase;
import com.example.asiancountries.database.CountryEntity;
import com.example.asiancountries.util.ConnectionManager;
import com.example.asiancountries.model.Country;
import com.example.asiancountries.adapter.HomeRecyclerAdapter;
import com.example.asiancountries.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerHome;
    private Toolbar toolbar;
    private TextView txtDelete;

    private RecyclerView.LayoutManager layoutManager;

    HomeRecyclerAdapter homeRecyclerAdapter;

    private ArrayList<Country> countryListInfo = new ArrayList<>();
    private Country countryObject;

    private RelativeLayout progressLayout;
    private ProgressBar progressBar;

    private Context contextn;

    List<CountryEntity> country = new  ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerHome = findViewById(R.id.recyclerHome);
        layoutManager = new LinearLayoutManager(this);

        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        progressLayout = findViewById(R.id.progressLayout);
        txtDelete = findViewById(R.id.txtDelete);

        progressLayout.setVisibility(View.VISIBLE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Asian Countries");

        contextn = this;

        try {
            country = new RetrieveAllCountries(contextn).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final List<CountryEntity> finalCountry2 = country;
        txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DBAsyncTask(MainActivity.this, new CountryEntity(1,"","","","","",1,"",""), 3).execute();

            }
        });



        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://restcountries.eu/rest/v2/region/asia";

        if (new ConnectionManager().checkConnectivity(this)) {
            final List<CountryEntity> finalCountry1 = country;
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {

                    try {
                        progressLayout.setVisibility(View.GONE);

                        for (int i=0;i<response.length();i++) {
                            JSONObject countryJsonObject = response.getJSONObject(i);
                            String name = countryJsonObject.getString("name");
                            String capital = countryJsonObject.getString("capital");
                            String flag = countryJsonObject.getString("flag");
                            String region = countryJsonObject.getString("region");
                            String subregion = countryJsonObject.getString("subregion");
                            int population = countryJsonObject.getInt("population");
                            JSONArray borders = countryJsonObject.getJSONArray("borders");
                            JSONArray languages = countryJsonObject.getJSONArray("languages");

                            String allBorders=null;

                            if (borders.length()!=0) {
                                allBorders = borders.getString(0);

                                for (int j=1;j<borders.length();j++) {
                                    allBorders = allBorders+", "+borders.getString(j);
                                }
                            } else {
                                allBorders = "none";
                            }

                            String allLanguages=null;

                            if (languages.length()!=0) {
                                allLanguages = languages.getJSONObject(0).getString("name");

                                for (int k=1;k<languages.length();k++) {
                                    allLanguages = allLanguages+", "+languages.getJSONObject(k).getString("name");
                                }

                            } else {
                                allLanguages = "none";
                            }

                            countryObject =new Country(name, capital, flag, region, subregion, population, allBorders, allLanguages);

                            countryListInfo.add(countryObject);
                            homeRecyclerAdapter = new HomeRecyclerAdapter(MainActivity.this, countryListInfo);
                            recyclerHome.setAdapter(homeRecyclerAdapter);
                            recyclerHome.setLayoutManager(layoutManager);


                            CountryEntity countryEntity = new CountryEntity(i,name, capital, flag, region, subregion, population, allBorders, allLanguages);

                            if (finalCountry1.size() < countryListInfo.size()) {
                                AsyncTask<Void, Void, Boolean> async = new DBAsyncTask(MainActivity.this, countryEntity, 2).execute();
                            }

                        }
                    } catch(Exception e) {
                        progressLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    progressLayout.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

            queue.add(jsonObjectRequest);
        } else if (country.size()!=0) {
            progressLayout.setVisibility(View.GONE);
            for (int i=0;i<country.size();i++) {
                Country countryObj = new Country(
                        country.get(i).getCountryName(),
                        country.get(i).getCapital(),
                        country.get(i).getFlag(),
                        country.get(i).getRegion(),
                        country.get(i).getSubregion(),
                        country.get(i).getPopulation(),
                        country.get(i).getBorders(),
                        country.get(i).getLanguages()
                );
                countryListInfo.add(countryObj);
                homeRecyclerAdapter = new HomeRecyclerAdapter(MainActivity.this, countryListInfo);
                recyclerHome.setAdapter(homeRecyclerAdapter);
                recyclerHome.setLayoutManager(layoutManager);
            }
        }
    }

    class RetrieveAllCountries extends AsyncTask<Void, Void, List<CountryEntity>> {

        Context context;

        public RetrieveAllCountries(Context context) {
            this.context = context;
        }

        CountryDatabase db= Room.databaseBuilder(MainActivity.this, CountryDatabase.class, "db-country").build();

        @Override
        protected List<CountryEntity> doInBackground(Void... voids) {
            List<CountryEntity> country2 = db.countryDao().getAllRestaurants();
            db.close();
            return country2;
        }
    }

    class DBAsyncTask extends AsyncTask<Void, Void, Boolean> {
        Context context;
        CountryEntity countryEntity;
        int mode;

        public DBAsyncTask(Context context, CountryEntity countryEntity, int mode) {
            this.context = context;
            this.countryEntity = countryEntity;
            this.mode = mode;
        }

        CountryDatabase db= Room.databaseBuilder(MainActivity.this, CountryDatabase.class, "db-country").build();

        @Override
        protected Boolean doInBackground(Void... voids) {

            switch (mode) {
                case 1 : {
                    List<CountryEntity> country = db.countryDao().getAllRestaurants();
                    db.close();
                    return country!=null;
                }
                case 2 : {
                    db.countryDao().insertCountry(countryEntity);
                    db.close();
                    return true;
                }

                case 3 : {
                    db.countryDao().deleteCountry();
                    db.close();
                    return true;
                }
            }

            return false;
        }
    }
}
