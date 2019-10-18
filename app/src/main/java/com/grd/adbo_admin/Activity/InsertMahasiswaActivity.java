package com.grd.adbo_admin.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.grd.adbo_admin.Model.DosenModel;
import com.grd.adbo_admin.Model.ResultDosenModel;
import com.grd.adbo_admin.Model.ValueModel;
import com.grd.adbo_admin.R;
import com.grd.adbo_admin.Retrofit.ApiClient;
import com.grd.adbo_admin.Retrofit.RegisterAPI;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertMahasiswaActivity extends AppCompatActivity {
    private TextView tvNIM, tvNama, tvDosenWali;
    private EditText etNIM,etNama,etPin;
    private RadioGroup radioGroupjenis;
    private RadioButton rbjeniskelamin;
    private Button btnDaftar;
    private Spinner spinnerDosen;
    private ProgressDialog progress;
    private List<ResultDosenModel> results = new ArrayList<>();
    RegisterAPI mApiInterface;
    int kirimnipdosenwali;
    String nim, nama, jenis_kelamin, semester,pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_mahasiswa);
        etNIM = (EditText) findViewById(R.id.editTextNPM);
        etNama = (EditText) findViewById(R.id.editTextNama);
        etPin = (EditText) findViewById(R.id.editTextPin);
        radioGroupjenis = (RadioGroup) findViewById(R.id.radioJenis);
        btnDaftar = (Button) findViewById(R.id.buttonDaftar);
        spinnerDosen = (Spinner) findViewById(R.id.spinDosen);

        mApiInterface = ApiClient.getClient().create(RegisterAPI.class);
        populateSpinner();
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etNIM.getText()) ||
                        TextUtils.isEmpty(etNama.getText()) ||
                        TextUtils.isEmpty(etPin.getText())) {
                    Toast.makeText(InsertMahasiswaActivity.this,
                            "All column is required", Toast.LENGTH_SHORT).show();
                } else {
                    daftar();
                }
            }
        });
    }

    private void populateSpinner() {
        mApiInterface.getSemuaDosen().enqueue(new Callback<DosenModel>() {
            @Override
            public void onResponse(Call<DosenModel> call, Response<DosenModel> response) {
                if (response.isSuccessful()) {
                    String value = response.body().getValue();
                    if (value.equals("1")) {
                        List<ResultDosenModel> semuadosenItems = response.body().getResult();
                        List<String> listSpinner = new ArrayList<String>();
                        for (int i = 0; i < semuadosenItems.size(); i++) {
                            listSpinner.add(semuadosenItems.get(i).getNip() + " - " +
                                    semuadosenItems.get(i).getNama_dosen());
                        }
                        // Set hasil result json ke dalam adapter spinner
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (InsertMahasiswaActivity.this,
                                android.R.layout.simple_spinner_item, listSpinner);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDosen.setAdapter(adapter);
                    } else {
                        Toast.makeText(InsertMahasiswaActivity.this, "Gagal mengambil data dosen", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DosenModel> call, Throwable t) {
                Toast.makeText(InsertMahasiswaActivity.this,
                        "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void daftar() {
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();

        nim = etNIM.getText().toString();
        nama = etNama.getText().toString();
        pin = etPin.getText().toString();
        int selectedId = radioGroupjenis.getCheckedRadioButtonId();
        // mencari id radio button
        rbjeniskelamin = (RadioButton) findViewById(selectedId);
        jenis_kelamin = rbjeniskelamin.getText().toString();
        kirimnipdosenwali=Integer.parseInt(spinnerDosen.getSelectedItem().toString().substring(0,3));
        String getsmt = "20"+nim.substring(3,5);
        semester = String.valueOf((Calendar.getInstance().get(Calendar.YEAR)-Integer.parseInt(getsmt))*2);
        Call<ValueModel> valueModelCall =
                mApiInterface.daftar(nim,nama,jenis_kelamin,semester,kirimnipdosenwali, pin);
        valueModelCall.enqueue(new Callback<ValueModel>() {
            @Override
            public void onResponse(Call<ValueModel> call, Response<ValueModel> response) {
                String value = response.body().getValue();
                String message = response.body().getMessage();
                progress.dismiss();
                if (value.equals("1")) {
                    Toast.makeText(InsertMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                    etNIM.setText("");
                    etNama.setText("");
                    etPin.setText("");
                    rbjeniskelamin.setSelected(false);
                    startActivity(new Intent(InsertMahasiswaActivity.this, ViewMahasiswaActivity.class));
                    finish();
                } else {
                    Toast.makeText(InsertMahasiswaActivity.this, message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ValueModel> call, Throwable t) {
                Toast.makeText(InsertMahasiswaActivity.this, "Error connection!", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
