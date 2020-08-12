package com.nta.annaplay.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nta.annaplay.Adapter.BannerAdapter;
import com.nta.annaplay.Model.QuangCao;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {

    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Gán layout vào fragment và ánh xạ:
        view = inflater.inflate(R.layout.fragment_banner,container,false);
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.indicatordefault);
        GetData();
        return view;
    }
        //Xử lý dữ liệu bảng quảng cáo:
        private void GetData() {
            DataService dataservice = APIService.getService();
            Call<List<QuangCao>> callback = dataservice.GetDataBanner();
            //Lắng nghe dữ liệu trả về:
            callback.enqueue(new Callback<List<QuangCao>>() {
                //Lắng nghe cho việc dữ liệu trả về thành công:
                @Override
                public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                    ArrayList<QuangCao> banners = (ArrayList<QuangCao>) response.body();
                    //Cấu hình lại cho viewPager:
                    bannerAdapter = new BannerAdapter(getActivity(),banners);
                    viewPager.setAdapter(bannerAdapter);
                    circleIndicator.setViewPager(viewPager);
                    handler = new Handler();
                    //Thời gian chờ cho việc chuyển quảng cáo:
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            currentItem = viewPager.getCurrentItem();
                            currentItem++;
                            if(currentItem >=viewPager.getAdapter().getCount()){
                                currentItem = 0;
                            }
                            viewPager.setCurrentItem(currentItem,true);
                            handler.postDelayed(runnable,4500);
                        }
                    };
                    handler.postDelayed(runnable,4500);
                }
                //Lắng nghe cho việc dữ liệu trả về thất bại:
                @Override
                public void onFailure(Call<List<QuangCao>> call, Throwable t) {

                }
            });
        }
}
