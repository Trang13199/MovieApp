package com.mytrang.moviesapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.mytrang.moviesapplication.Service.MovieService;
import com.mytrang.moviesapplication.Util.APIUtil;
import com.mytrang.moviesapplication.adapter.MovieAdapter;
import com.mytrang.moviesapplication.model.Data;
import com.mytrang.moviesapplication.model.Movies;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
    private MovieService movieService;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movies> arrayList;
    private ProgressDialog progressDialog;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.list_view);

        actionBar = getSupportActionBar();
        actionBar.setTitle("HFILM");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F43D04")));
//        actionBar.

        progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();


        movieService = APIUtil.getMovieService();

        adapter = new MovieAdapter(this, new ArrayList<Movies>(0), new MovieAdapter.MovieItemListener() {
            @Override
            public void onPostClick(long id) {
                Toast.makeText(ListActivity.this, "Post id is" + id, Toast.LENGTH_LONG).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        loadAnswer();

    }

    private void loadAnswer() {
        movieService.getAnswer().enqueue(new Callback<Data>() {

            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    adapter.updateAnswer(response.body().getData());
                    arrayList = new Data().getData();
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");
            }
        });
    }
}