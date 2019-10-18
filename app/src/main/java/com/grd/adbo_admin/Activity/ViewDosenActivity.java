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
import com.grd.adbo_admin.Adapter.MahasiswaAdapter;
import com.grd.adbo_admin.Model.DosenModel;
import com.grd.adbo_admin.Model.ResultDosenModel;
import com.grd.adbo_admin.Model.ResultMahasiswaModel;
import com.grd.adbo_admin.Model.ValueModel;
import com.grd.adbo_admin.R;
import com.grd.adbo_admin.Retrofit.ApiClient;
import com.grd.adbo_admin.Retrofit.RegisterAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDosenActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DosenAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private List<ResultDosenModel> results = new ArrayList<>();
    RegisterAPI mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_dosen);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new DosenAdapter(this, results);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loaddatadosen();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewDosenActivity.this,
                        InsertDosenActivity.class));
            }
        });
    }

    public void loaddatadosen() {
        mApiInterface = ApiClient.getClient().create(RegisterAPI.class);
        Call<DosenModel> dosenModelCall = mApiInterface.getSemuaDosen();
        dosenModelCall.enqueue(new Callback<DosenModel>() {
            @Override
            public void onResponse(Call<DosenModel> call, Response<DosenModel> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    mAdapter = new DosenAdapter(ViewDosenActivity.this, results);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<DosenModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddatadosen();
    }
}
