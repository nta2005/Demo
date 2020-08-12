package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nta.annaplay.Adapter.BaiHatAdapter;
import com.nta.annaplay.Model.Album;
import com.nta.annaplay.Model.BaiHat;
import com.nta.annaplay.Model.PlayList;
import com.nta.annaplay.Model.QuangCao;
import com.nta.annaplay.Model.TheLoai;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaiHatActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewdsbaihat;
    FloatingActionButton floatingActionButton;
    QuangCao quangcao;
    ImageView imgdanhsachcakhuc;
    ArrayList<BaiHat> mangbaihat;
    BaiHatAdapter baiHatAdapter;
    PlayList playlist;
    TheLoai theLoai;
    Album album;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachbaihat);
        //Kiểm tra mạng:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        anhxa();
        init();
        //Kiểm tra dữ liệu trả về và gán lên view:
        if (quangcao != null && !quangcao.getTenBaiHat().equals("")){
            setValueInView(quangcao.getTenBaiHat(),quangcao.getHinhBaiHat());
            GetDataQuangCao(quangcao.getIdQuangCao());
        }

        if (playlist != null && !playlist.getTen().equals("")){
            setValueInView(playlist.getTen(),playlist.getIcon());
            GetDataPlaylist(playlist.getIdPlaylist());
        }
        if (theLoai !=null &&!theLoai.getTenTheLoai().equals("")){
            setValueInView(theLoai.getTenTheLoai(),theLoai.getHinhTheLoai());
            GetDataTheLoai(theLoai.getIdTheLoai());
        }

        if (album !=null &&!album.getTenAlbum().equals("")){
            setValueInView(album.getTenAlbum(),album.getHinhanhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }
    }
    //Gán dữ liệu:
    private void setValueInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imgdanhsachcakhuc);
    }
    //Xử lý dữ liệu Quảng cáo:
    private void GetDataQuangCao(String idquangcao) {
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachBaiHatTheoQuangCao(idquangcao);
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<BaiHat>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>)response.body();
                baiHatAdapter = new BaiHatAdapter(BaiHatActivity.this,mangbaihat);
                recyclerViewdsbaihat.setLayoutManager(new LinearLayoutManager(BaiHatActivity.this));
                recyclerViewdsbaihat.setAdapter(baiHatAdapter);
                eventClick();
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    //Xử lý dữ liệu Playlist:
    private void GetDataPlaylist(String idplaylist) {
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachBaiHatTheoPlaylist(idplaylist);
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<BaiHat>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                baiHatAdapter = new BaiHatAdapter(BaiHatActivity.this,mangbaihat);
                recyclerViewdsbaihat.setLayoutManager(new LinearLayoutManager(BaiHatActivity.this));
                recyclerViewdsbaihat.setAdapter(baiHatAdapter);
                eventClick();
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    //Xử lý dữ liệu Playlist:
    private void GetDataTheLoai(String idtheloai){
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachBaiHatTheoTheLoai(idtheloai);
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<BaiHat>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                baiHatAdapter = new BaiHatAdapter(BaiHatActivity.this,mangbaihat);
                recyclerViewdsbaihat.setLayoutManager(new LinearLayoutManager(BaiHatActivity.this));
                recyclerViewdsbaihat.setAdapter(baiHatAdapter);
                eventClick();
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
    //Xử lý dữ liệu Album:
    private void GetDataAlbum(String idAlbum) {
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhSachBaiHatTheoAlbum(idAlbum);
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<BaiHat>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangbaihat = (ArrayList<BaiHat>) response.body();
                baiHatAdapter = new BaiHatAdapter(BaiHatActivity.this,mangbaihat);
                recyclerViewdsbaihat.setLayoutManager(new LinearLayoutManager(BaiHatActivity.this));
                recyclerViewdsbaihat.setAdapter(baiHatAdapter);
                eventClick();
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);

    }

    private void anhxa() {
        coordinatorLayout = findViewById(R.id.coordinatorlayout);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsach);
        recyclerViewdsbaihat = findViewById(R.id.recyclerviewdanhsachbaihat);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        imgdanhsachcakhuc = findViewById(R.id.imageviewdanhsachcakhuc);
    }

    //Kiểm tra Intent:
    private void DataIntent() {
        Intent intent = getIntent();
        if(intent !=null){
            if(intent.hasExtra("banner")){
                quangcao = (QuangCao) intent.getSerializableExtra("banner");
            }
            if(intent.hasExtra("itemplaylist")){
                playlist = (PlayList) intent.getSerializableExtra("itemplaylist");

            }
            if(intent.hasExtra("idtheloai")){
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if(intent.hasExtra("album")){
                album = (Album) intent.getSerializableExtra("album");
            }

        }
    }
    //Sự kiện khi nhấn vào FloatingButton:
    private void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiHatActivity.this,PlayNhacActivity.class);
                intent.putExtra("cacbaihat",mangbaihat);
                startActivity(intent);

            }
        });
    }
}
