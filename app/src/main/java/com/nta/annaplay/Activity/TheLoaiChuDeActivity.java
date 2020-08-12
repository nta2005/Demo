package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.nta.annaplay.Adapter.TheLoaiChuDeAdapter;
import com.nta.annaplay.Model.ChuDe;
import com.nta.annaplay.Model.TheLoai;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TheLoaiChuDeActivity extends AppCompatActivity {
    ChuDe chuDe;
    RecyclerView recyclerViewtheloaitheochude;
    Toolbar toolbartheloaitheochude;
    TheLoaiChuDeAdapter theLoaiChuDeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtheloaitheochude);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        GetIntent();
        init();
        GetData();

    }
    //Xử lý dữ liệu:
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<TheLoai>> callback = dataservice.GetTheLoaiTheoChuDe(chuDe.getIdChuDe());
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<TheLoai>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangtheloai = (ArrayList<TheLoai>) response.body();
                theLoaiChuDeAdapter = new TheLoaiChuDeAdapter(TheLoaiChuDeActivity.this,mangtheloai);
                recyclerViewtheloaitheochude.setLayoutManager(new GridLayoutManager(TheLoaiChuDeActivity.this,2));
                recyclerViewtheloaitheochude.setAdapter(theLoaiChuDeAdapter);
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {

            }
        });
    }

    private void init() {
        recyclerViewtheloaitheochude = findViewById(R.id.recyclerviewtheloaitheochude);
        toolbartheloaitheochude = findViewById(R.id.toolbartheloaitheochude);
        setSupportActionBar(toolbartheloaitheochude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbartheloaitheochude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //Kiểm tra Intent:
    private void GetIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra("chude")){
            chuDe = (ChuDe) intent.getSerializableExtra("chude");
        }
    }
}
