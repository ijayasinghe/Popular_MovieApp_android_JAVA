// first update the androidManifest.xml and build.gradle module
// crete Volleysingleton class for request handling
//create Movie class to hold data we recievce from the API
//recyclerview class for creating the recyclerview - also create a cardview layout file to create the look for a one instance of viewholder

package com.example.popularmovies;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;

    private RequestQueue requestQueue;
    private ArrayList<Movie> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        movieList = new ArrayList<>();
        fetchMovies();


    }

    //this is to fetch the data from the API and store it in the movie list as movie objects
    private void fetchMovies() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=755026b27d3a5304cc8aa5f2ff278924&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONArray jsonArray = response.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    String overview = jsonObject.getString("overview");
//                    double rating = jsonObject.getDouble("vote_average");
                    String rating = String.format("%.1f",jsonObject.getDouble("vote_average"));
                    String imgUrl = "https://image.tmdb.org/t/p/w300" + jsonObject.getString("poster_path");

                    Movie movie = new Movie(title, imgUrl, rating, overview);
                    movieList.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RecyclerAdopter adopter = new RecyclerAdopter(MainActivity.this, movieList);
            recyclerView.setAdapter(adopter);
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}