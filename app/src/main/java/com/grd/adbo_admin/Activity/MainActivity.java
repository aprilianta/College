package com.grd.adbo_admin.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.grd.adbo_admin.R;
import com.grd.adbo_admin.SharedPrefManager;

public class MainActivity extends AppCompatActivity {
    CardView cvlogout,cvdosen,cvkajur,cvmahasiswa;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cvmahasiswa = (CardView) findViewById(R.id.cvmahasiswa);
        cvdosen = (CardView) findViewById(R.id.cvdosen);
        cvkajur = (CardView) findViewById(R.id.cvkajur);
        cvlogout = (CardView) findViewById(R.id.cvlogout);
        sharedPrefManager = new SharedPrefManager(this);
        cvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
            }
        });
        cvmahasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewMahasiswaActivity.class));
            }
        });
        cvdosen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewDosenActivity.class));
            }
        });
        cvkajur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ViewKajurActivity.class));
            }
        });
    }
}
