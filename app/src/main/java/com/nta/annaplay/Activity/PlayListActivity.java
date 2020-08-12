package com.nta.annaplay.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import com.nta.annaplay.Adapter.PlayListsAdapter;
import com.nta.annaplay.Model.PlayList;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayListActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachcacplaylist;
    PlayListsAdapter DSPlaylistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachcacplaylist);
        //Kiểm tra mạng:
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Ánh xạ:
        toolbar = findViewById(R.id.toolbardanhsachcacplaylist);
        recyclerViewdanhsachcacplaylist = findViewById(R.id.recyclerviewdanhsachcacplaylist);
        init();
        GetData();
    }
    //Xử lý dữ liệu:
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<PlayList>> callback = dataservice.GetDanhSachCacPlaylist();
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<PlayList>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                ArrayList<PlayList> mangplaylist = (ArrayList<PlayList>) response.body();
                DSPlaylistAdapter = new PlayListsAdapter(PlayListActivity.this,mangplaylist);
                recyclerViewdanhsachcacplaylist.setLayoutManager(new GridLayoutManager(PlayListActivity.this,2));
                recyclerViewdanhsachcacplaylist.setAdapter(DSPlaylistAdapter);
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }

    private void init() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách các Playlist:");
        toolbar.setTitleTextColor(getResources().getColor(R.color.mautim));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
