package com.example.movieapi;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout noNetworkState;
    private Handler handler;
    private Runnable runnable;
    private RecyclerView recyclerView;
    private LinearLayout dataLayout;
    private RequestQueue requestQueue;

    private ArrayList<MovieData> movieList;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private SearchView searchView;
    private final String api = "https://2d059e27-1f60-4f9e-8ad7-ce238f57bb53.mock.pstmn.io";

    private Boolean isDataChanged = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noNetworkState = findViewById(R.id.noNetworkState);
        dataLayout = findViewById(R.id.dataLayout);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestQueue = Volley.newRequestQueue(this);
        progressBar = findViewById(R.id.progressBar);
        searchView = findViewById(R.id.searchMovie);
        movieList = new ArrayList<>();
        adapter = new MovieAdapter(movieList);
        recyclerView.setAdapter(adapter);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(isNetworkAvailable()){
                    if(!isDataChanged){
                        fetchApi();
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                filterList(newText);
                                return true;
                            }
                        });

                    }
                    showRecycleviewLayout();
                }else{
                    showNoInternetLayout();
                }
                handler.postDelayed(this,5000);
            }
        };
        handler.post(runnable);


    }

    private void filterList(String text) {
        ArrayList<MovieData> filterList = new ArrayList<>();
        for(MovieData movie:movieList){
            if(movie.getTitle().toLowerCase().contains(text.toLowerCase())){
                filterList.add(movie);
            }
        }
        if(filterList.isEmpty()){
            Toast.makeText(this,"No Movie Found",Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilterList(filterList);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }


    private void fetchApi() {
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility(View.GONE);
                for(int i = 0; i<response.length();i++){
                    try {

                        JSONObject jsonObject = response.getJSONObject(i);
                        String title = jsonObject.getString("title");
                        String overview = jsonObject.getString("overview");
                        String poster = jsonObject.getString("poster");
                        Double rating = jsonObject.getDouble("rating");
                        MovieData movie = new MovieData(title,poster,overview,rating);
                        movieList.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                adapter.notifyDataSetChanged();
                isDataChanged = true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void showRecycleviewLayout(){
        noNetworkState.setVisibility(View.GONE);
        dataLayout.setVisibility(View.VISIBLE);
    }
    private void showNoInternetLayout() {
        noNetworkState.setVisibility(View.VISIBLE);
        dataLayout.setVisibility(View.GONE);
    }
    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}