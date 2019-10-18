package com.grd.adbo_admin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grd.adbo_admin.Activity.UpdateMahasiswaActivity;
import com.grd.adbo_admin.Model.ResultMahasiswaModel;
import com.grd.adbo_admin.R;

import java.util.List;

public class MahasiswaAdapter extends RecyclerView.
        Adapter<MahasiswaAdapter.ViewHolder> {


    private Context context;
    private List<ResultMahasiswaModel> results;

    public MahasiswaAdapter(Context context, List<ResultMahasiswaModel> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultMahasiswaModel result = results.get(position);
        holder.textViewNIM.setText(result.getNim());
        holder.textViewNama.setText(result.getNama());
        holder.textViewJenisKelamin.setText(result.getJenis_kelamin());
        holder.textViewSemester.setText(result.getSemester());
        holder.textViewDosenWali.setText(result.getNama_dosen());
        holder.textViewPin.setText(result.getPin());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNIM,textViewNama,textViewJenisKelamin,textViewSemester,
                textViewDosenWali,textViewPin;
        String nim, nama, jenis_kelamin, semester, dosenwali, pin;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNIM = (TextView) itemView.findViewById(R.id.textNIM);
            textViewNama = (TextView) itemView.findViewById(R.id.textNama);
            textViewSemester = (TextView) itemView.findViewById(R.id.textSemester);
            textViewJenisKelamin = (TextView) itemView.findViewById(R.id.textJenisKelamin);
            textViewDosenWali = (TextView) itemView.findViewById(R.id.textDosenWali);
            textViewPin = (TextView) itemView.findViewById(R.id.textPin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nim = textViewNIM.getText().toString();
                    nama = textViewNama.getText().toString();
                    jenis_kelamin = textViewJenisKelamin.getText().toString();
                    dosenwali = textViewDosenWali.getText().toString();
                    pin = textViewPin.getText().toString();
                    Intent i = new Intent(context, UpdateMahasiswaActivity.class);
                    i.putExtra("nim", nim);
                    i.putExtra("nama", nama);
                    i.putExtra("jenis_kelamin", jenis_kelamin);
                    i.putExtra("dosen_wali",dosenwali);
                    i.putExtra("pin",pin);
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            });
        }
    }
}
