package com.nta.annaplay.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.annaplay.Activity.AlbumActivity;
import com.nta.annaplay.Adapter.AlbumAdapter;
import com.nta.annaplay.Model.Album;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Album_Hot extends Fragment {
    View view;
    RecyclerView recyclerViewalbum;
    TextView txtxemthemalbum;
    AlbumAdapter albumAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Gán layout vào fragment và ánh xạ:
        view = inflater.inflate(R.layout.fragment_album_hot,container,false);
        recyclerViewalbum = view.findViewById(R.id.recyclerviewalbum);
        txtxemthemalbum = view.findViewById(R.id.textviewxemthemAlbum);
        //Sự kiện khi nhấn vào text Xem thêm của album:
        txtxemthemalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AlbumActivity.class);
                startActivity(intent);
            }
        });
        GetData();
        return view;
    }
    //Xử lý dữ liệu:
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<Album>> callback = dataservice.GetAlbumHot();
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<Album>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList = (ArrayList<Album>)response.body();
                albumAdapter = new AlbumAdapter(getActivity(),albumArrayList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                recyclerViewalbum.setLayoutManager(linearLayoutManager);
                recyclerViewalbum.setAdapter(albumAdapter);

            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }
}
