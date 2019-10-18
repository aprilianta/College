package com.grd.adbo_admin.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grd.adbo_admin.Activity.UpdateDosenActivity;
import com.grd.adbo_admin.Activity.UpdateMahasiswaActivity;
import com.grd.adbo_admin.Model.ResultDosenModel;
import com.grd.adbo_admin.Model.ResultMahasiswaModel;
import com.grd.adbo_admin.R;

import java.util.List;

public class DosenAdapter extends RecyclerView.
        Adapter<DosenAdapter.ViewHolder> {


    private Context context;
    private List<ResultDosenModel> results;

    public DosenAdapter(Context context, List<ResultDosenModel> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_dosen, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ResultDosenModel result = results.get(position);
        holder.textViewNIP.setText(result.getNip());
        holder.textViewNama.setText(result.getNama_dosen());
        holder.textViewJenisKelamin.setText(result.getJenis_kelamin());
        holder.textViewPin.setText(result.getPin());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNIP,textViewNama,textViewJenisKelamin, textViewPin;
        String nip, nama, jenis_kelamin, pin;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewNIP = (TextView) itemView.findViewById(R.id.textNIM);
            textViewNama = (TextView) itemView.findViewById(R.id.textNama);
            textViewJenisKelamin = (TextView) itemView.findViewById(R.id.textJenisKelamin);
            textViewPin = (TextView)itemView.findViewById(R.id.textPin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nip = textViewNIP.getText().toString();
                    nama = textViewNama.getText().toString();
                    jenis_kelamin = textViewJenisKelamin.getText().toString();
                    pin = textViewPin.getText().toString();
                    Intent i = new Intent(context, UpdateDosenActivity.class);
                    i.putExtra("nim", nip);
                    i.putExtra("nama", nama);
                    i.putExtra("jenis_kelamin", jenis_kelamin);
                    i.putExtra("pin", pin);
                    context.startActivity(i);
                    ((Activity)context).finish();
                }
            });
        }
    }
}
