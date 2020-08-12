package com.nta.annaplay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.annaplay.Activity.BaiHatActivity;
import com.nta.annaplay.Activity.PlayNhacActivity;
import com.nta.annaplay.Fragment.Fragment_Play_Danh_Sach_Cac_Bai_Hat;
import com.nta.annaplay.Model.BaiHat;
import com.nta.annaplay.R;

import java.util.ArrayList;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> mangbaihat;

    public PlayNhacAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_playnhac,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = mangbaihat.get(position);
        holder.txt_casi.setText(baihat.getCasi());
        holder.txt_index.setText(position + 1 + "");
        holder.txt_tenbaihat.setText(baihat.getTenbaihat());
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_index,txt_tenbaihat,txt_casi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_casi = itemView.findViewById(R.id.textview_playnhac_tencasi);
            txt_tenbaihat = itemView.findViewById(R.id.textview_playnhac_tenbaihat);
            txt_index = itemView.findViewById(R.id.textview_playnhac_index);
        }
    }

}
