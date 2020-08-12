package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.nta.annaplay.Adapter.ChuDeAdapter;
import com.nta.annaplay.Model.ChuDe;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChuDeActivity extends AppCompatActivity {
    RecyclerView recyclerViewtatcachude;
    Toolbar toolbartatcachude;
    ChuDeAdapter chudeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcachude);
        //Kiểm tra mạng:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init();
        GetData();
    }

    private void init() {
        recyclerViewtatcachude = findViewById(R.id.recyclerviewAllChude);
        toolbartatcachude = findViewById(R.id.toolbarallchude);
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả các chủ đề:");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Xử lý dữ liệu
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<ChuDe>> callback = dataservice.GetAllChuDe();
        //Lắng nghe dữ liệu:
        callback.enqueue(new Callback<List<ChuDe>>() {
            //Thành công:
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangchude = (ArrayList<ChuDe>) response.body();
                chudeAdapter = new ChuDeAdapter(ChuDeActivity.this,mangchude);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(ChuDeActivity.this,1));
                recyclerViewtatcachude.setAdapter(chudeAdapter);
            }
            //Thất bại:
            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });

    }
}
