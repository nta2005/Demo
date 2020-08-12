package com.nta.annaplay.Adapter;
//Adapter thiết kế giao diện danh sách bài hát:
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.annaplay.Activity.PlayNhacActivity;
import com.nta.annaplay.Model.BaiHat;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiHatAdapter extends RecyclerView.Adapter<BaiHatAdapter.ViewHolder>{

    Context context;
    ArrayList<BaiHat> mangbaihat;

    public BaiHatAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danh_sach_bai_hat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baihat = mangbaihat.get(position);
        holder.txtcasi.setText(baihat.getCasi());
        holder.txttenbaihat.setText(baihat.getTenbaihat());
        holder.txtindex.setText(position + 1 + "");
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView txtindex,txttenbaihat,txtcasi;
       ImageView imgluotthich;
       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           txtcasi = itemView.findViewById(R.id.textviewtencasi);
           txttenbaihat = itemView.findViewById(R.id.textviewtenbaihat);
           txtindex = itemView.findViewById(R.id.textviewdanhsachindex);
           imgluotthich = itemView.findViewById(R.id.imageviewluotthichdanhsachbaihat);
           //Sự kiện khi nhấn vào hình trái tim:
           imgluotthich.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   imgluotthich.setImageResource(R.drawable.iconloved);
                   DataService dataservice = APIService.getService();
                   Call<String> callback = dataservice.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdbaihat());
                   callback.enqueue(new Callback<String>() {
                       @Override
                       public void onResponse(Call<String> call, Response<String> response) {
                           String ketqua = response.body();
                           if(ketqua.equals("OK")){
                               Toast.makeText(context, "Đã thích!", Toast.LENGTH_SHORT).show();
                           }else {
                               Toast.makeText(context, "Lỗi!!!", Toast.LENGTH_SHORT).show();
                           }
                       }

                       @Override
                       public void onFailure(Call<String> call, Throwable t) {

                       }
                   });
                   imgluotthich.setEnabled(false);
               }
           });
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(context, PlayNhacActivity.class);
                   intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                   context.startActivity(intent);
               }
           });
       }
   }
}
