package com.grd.adbo_admin.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.grd.adbo_admin.Adapter.DosenAdapter;
import com.grd.adbo_admin.Adapter.KajurAdapter;
import com.grd.adbo_admin.Model.DosenModel;
import com.grd.adbo_admin.Model.KajurModel;
import com.grd.adbo_admin.Model.ResultDosenModel;
import com.grd.adbo_admin.Model.ResultKajurModel;
import com.grd.adbo_admin.R;
import com.grd.adbo_admin.Retrofit.ApiClient;
import com.grd.adbo_admin.Retrofit.RegisterAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewKajurActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private KajurAdapter mAdapter;
    private android.support.v7.widget.RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private List<ResultKajurModel> results = new ArrayList<>();
    RegisterAPI mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_kajur);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new KajurAdapter(this, results);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loaddatakajur();
    }
    public void loaddatakajur() {
        mApiInterface = ApiClient.getClient().create(RegisterAPI.class);
        Call<KajurModel> kajurModelCall = mApiInterface.view_kajur();
        kajurModelCall.enqueue(new Callback<KajurModel>() {
            @Override
            public void onResponse(Call<KajurModel> call, Response<KajurModel> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    mAdapter = new KajurAdapter(ViewKajurActivity.this, results);
                    mRecyclerView.setAdapter(mAdapter);
                    System.out.println(results.size());
                    if (results.size()<1) {
                        progressBar.setVisibility(View.INVISIBLE);
                        fab.setVisibility(View.VISIBLE);
                        fab.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(ViewKajurActivity.this,
                                        InsertKajurActivity.class));
                            }
                        });
                    } else {
                        fab.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<KajurModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddatakajur();
    }
}
