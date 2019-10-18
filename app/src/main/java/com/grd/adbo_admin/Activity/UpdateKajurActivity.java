package com.grd.adbo_admin.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.grd.adbo_admin.Model.KajurModel;
import com.grd.adbo_admin.Model.ResultKajurModel;
import com.grd.adbo_admin.R;
import com.grd.adbo_admin.Retrofit.ApiClient;
import com.grd.adbo_admin.Retrofit.RegisterAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateKajurActivity extends AppCompatActivity {
    private EditText etNIP, etNama, etPin;
    private RadioGroup radioGroupjenis;
    private RadioButton rbjeniskelamin, rbpria, rbwanita;
    private Button btnUpdate;
    private ProgressDialog progress;
    private List<ResultKajurModel> results = new ArrayList<>();
    RegisterAPI mApiInterface;
    String nip, nama, jenis_kelamin, pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_kajur);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ubah Data Kepala Jurusan");

        etNIP = (EditText) findViewById(R.id.editTextNIP);
        etNama = (EditText) findViewById(R.id.editTextNama);
        etPin = (EditText) findViewById(R.id.editTextPin);
        radioGroupjenis = (RadioGroup) findViewById(R.id.radioJenis);
        rbpria = (RadioButton) findViewById(R.id.radioPria);
        rbwanita = (RadioButton) findViewById(R.id.radioWanita);
        btnUpdate = (Button) findViewById(R.id.buttonUpdate);

        Intent intent = getIntent();
        nip = intent.getStringExtra("nim");
        nama = intent.getStringExtra("nama");
        pin = intent.getStringExtra("pin");
        jenis_kelamin = intent.getStringExtra("jenis_kelamin");

        etNIP.setText(nip);
        etNIP.setKeyListener((KeyListener) etNIP.getTag());
        etNama.setText(nama);
        etPin.setText(pin);

        if (jenis_kelamin.equals("Pria")) {
            rbpria.setChecked(true);
        } else {
            rbwanita.setChecked(true);
        }
        mApiInterface = ApiClient.getClient().create(RegisterAPI.class);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pin.isEmpty()) {
                    Toast.makeText(UpdateKajurActivity.this,
                            "PIN is required", Toast.LENGTH_SHORT).show();
                } else {
                    ubah_kajur();
                }
            }
        });
    }

    public void ubah_kajur() {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        nip = etNIP.getText().toString();
        nama = etNama.getText().toString();
        pin = etPin.getText().toString();
        int selectedId = radioGroupjenis.getCheckedRadioButtonId();
        // mencari id radio button
        rbjeniskelamin = (RadioButton) findViewById(selectedId);
        jenis_kelamin = rbjeniskelamin.getText().toString();

        Call<KajurModel> kajurModelCall =
                mApiInterface.ubah_kajur(nip, nama, jenis_kelamin, pin);
        kajurModelCall.enqueue(new Callback<KajurModel>() {
            @Override
            public void onResponse(Call<KajurModel> call, Response<KajurModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(UpdateKajurActivity.this, message, Toast.LENGTH_SHORT).show();
                    etNIP.setText("");
                    etNama.setText("");
                    etPin.setText("");
                    rbjeniskelamin.setSelected(false);
                    startActivity(new Intent(UpdateKajurActivity.this, ViewKajurActivity.class));
                    finish();
                } else {
                    Toast.makeText(UpdateKajurActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KajurModel> call, Throwable t) {
                Toast.makeText(UpdateKajurActivity.this, "Error connection!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(UpdateKajurActivity.this, ViewKajurActivity.class));
                finish();
                break;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Peringatan");
                alertDialogBuilder
                        .setMessage("Are you sure to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                String nipconvert = etNIP.getText().toString();
                                Call<KajurModel> call = mApiInterface.hapus_kajur(nipconvert);
                                call.enqueue(new Callback<KajurModel>() {
                                    @Override
                                    public void onResponse(Call<KajurModel> call, Response<KajurModel> response) {
                                        String value = response.body().getValue();
                                        String message = response.body().getMessage();
                                        if (value.equals("1")) {
                                            Toast.makeText(UpdateKajurActivity.this, message, Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(UpdateKajurActivity.this, message, Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<KajurModel> call, Throwable t) {
                                        t.printStackTrace();
                                        Toast.makeText(UpdateKajurActivity.this, "Jaringan Error!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
