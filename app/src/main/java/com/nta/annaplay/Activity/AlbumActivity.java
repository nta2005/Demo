package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.nta.annaplay.Adapter.AlbumsAdapter;
import com.nta.annaplay.Model.Album;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumActivity extends AppCompatActivity {
    RecyclerView recyclerViewAllalbum;
    Toolbar toolbaralbum;
    AlbumsAdapter albumsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatca_album);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        init();
        GetData();
    }
    //Lấy dữ liệu từ server:
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetAllAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangalbum = (ArrayList<Album>) response.body();
                albumsAdapter = new AlbumsAdapter(AlbumActivity.this,mangalbum);
                recyclerViewAllalbum.setLayoutManager(new GridLayoutManager(AlbumActivity.this,2));
                recyclerViewAllalbum.setAdapter(albumsAdapter);
            }
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
    //Ánh xạ, set text, gán sự kiện cho toolbar:
    private void init() {
       recyclerViewAllalbum = findViewById(R.id.recyclerviewAllAlbum);
       toolbaralbum = findViewById(R.id.toolbarAllalbum);
       setSupportActionBar(toolbaralbum);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle("Tất cả Album:");
       toolbaralbum.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }
}
