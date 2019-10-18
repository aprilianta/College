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

import com.grd.adbo_admin.Adapter.MahasiswaAdapter;
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

public class ViewMahasiswaActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MahasiswaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private List<ResultMahasiswaModel> results = new ArrayList<>();
    RegisterAPI mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MahasiswaAdapter(this, results);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        loaddatamahasiswa();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewMahasiswaActivity.this,
                        InsertMahasiswaActivity.class));
            }
        });
    }
    public void loaddatamahasiswa() {
        mApiInterface = ApiClient.getClient().create(RegisterAPI.class);
        Call<ValueModel> valueModelCall = mApiInterface.view();
        valueModelCall.enqueue(new Callback<ValueModel>() {
            @Override
            public void onResponse(Call<ValueModel> call, Response<ValueModel> response) {
                String value = response.body().getValue();
                progressBar.setVisibility(View.GONE);
                if (value.equals("1")) {
                    results = response.body().getResult();
                    mAdapter = new MahasiswaAdapter(ViewMahasiswaActivity.this, results);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onFailure(Call<ValueModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loaddatamahasiswa();
    }
}
