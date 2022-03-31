package com.mytrang.moviesapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mytrang.moviesapplication.Service.MovieService;
import com.mytrang.moviesapplication.Util.APIUtil;
import com.mytrang.moviesapplication.adapter.ItemClick;
import com.mytrang.moviesapplication.adapter.MovieAdapter;
import com.mytrang.moviesapplication.adapter.PaginationScrollListener;
import com.mytrang.moviesapplication.model.Data;
import com.mytrang.moviesapplication.model.Movies;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements ItemClick {
    private MovieService movieService;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private TextView toolBar, account;
//    private ProgressDialog progressDialog;

    private boolean isLoading;
    private boolean isLastPage;
    private int currentPage = 1;
    private int totalPage;
    private ProgressBar progressBar;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = (RecyclerView) findViewById(R.id.list_view);
        progressBar = findViewById(R.id.progress_bar);

        toolBar = findViewById(R.id.title_toolBar);
        toolBar.setText("HFILM");

//        actionBar = getSupportActionBar();
//        actionBar.setTitle("HFILM");
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F43D04")));


//        progressDialog = new ProgressDialog(ListActivity.this);
////        progressDialog.setCancelable(false);
//        progressDialog.setMax(100);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//
//        progressDialog.show();
//        progressDialog.setCancelable(false);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                progressDialog.dismiss();
//            }
//        }).start();


        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if (preferences.getString("token", "").isEmpty()) {
//                    Toast.makeText(ListActivity.this, "Ban chua dang nhap", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(ListActivity.this, LoginMainActivity.class);
//                    startActivity(intent);
                    account.setVisibility(View.GONE);
                } else {

                    editor.clear();
                    editor.commit();
                    Toast.makeText(ListActivity.this, "Logout success full", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ListActivity.this, ListActivity.class);
                    startActivity(intent);
                }
            }
        });

        movieService = APIUtil.getMovieService();

        adapter = new MovieAdapter(this, new ArrayList<Movies>(0), new MovieAdapter.MovieItemListener() {
            @Override
            public void onPostClick(long id) {
                Toast.makeText(ListActivity.this, "Post id is" + id, Toast.LENGTH_LONG).show();
            }
        });
        adapter.setItemClick(this::onClick);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        loadFirstAnswer();

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItems() {
                isLoading = true;
                progressBar.setVisibility(View.VISIBLE);
                currentPage += 1;
                loadNextPage();

            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });


    }

    private void loadNextPage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                movieService.getAnswer(String.valueOf(currentPage), "10").enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(Call<Data> call, Response<Data> response) {
                        if (response.isSuccessful()) {
                            adapter.insertArr(response.body().getData());
                            totalPage = response.body().getPaging().getTotalPages();
                        }
                    }

                    @Override
                    public void onFailure(Call<Data> call, Throwable t) {
                        Log.d("MainActivity", "Error loading from API");
                    }
                });
                isLoading = false;
                progressBar.setVisibility(View.GONE);
                if (currentPage == totalPage) {
                    isLoading = true;
                }

            }
        }, 2000);

    }

    private void loadFirstAnswer() {
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(ListActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        movieService.getAnswer(String.valueOf(currentPage), "10").enqueue(new Callback<Data>() {

            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if (response.isSuccessful()) {
                    adapter.updateAnswer(response.body().getData());
                    totalPage = response.body().getPaging().getTotalPages();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("MainActivity", "Error loading from API");
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(String s) {
        Intent login = new Intent(this, LoginMainActivity.class);
        startActivity(login);
        Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();
    }
}