package com.nta.annaplay.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.annaplay.Activity.PlayNhacActivity;
import com.nta.annaplay.Adapter.PlayNhacAdapter;
import com.nta.annaplay.R;

public class Fragment_Play_Danh_Sach_Cac_Bai_Hat extends Fragment {
    View view;
    RecyclerView recyclerView_Playnhac;
    PlayNhacAdapter playNhac_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_danh_sach_cac_bai_hat,container,false);
        recyclerView_Playnhac = view.findViewById(R.id.recyclerview_playbaihat);
        if(PlayNhacActivity.mangbaihat.size()>0){
            playNhac_adapter = new PlayNhacAdapter(getActivity(), PlayNhacActivity.mangbaihat);
            recyclerView_Playnhac.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView_Playnhac.setAdapter(playNhac_adapter);
        }

        return view;
    }
}
