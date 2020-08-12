package com.nta.annaplay.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nta.annaplay.Adapter.SearchBaiHatAdapter;
import com.nta.annaplay.Model.BaiHat;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Search extends Fragment {
    Toolbar toolbar_search;
    RecyclerView recyclerView_search;
    TextView txt_none;
    LinearLayout linearLayout, lnls1, lnls2;
    SearchBaiHatAdapter searchBaiHatAdapter;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Gán layout vào fragment:
        view = inflater.inflate(R.layout.fragment_search,container,false);
        toolbar_search = view.findViewById(R.id.toolbar_search);
        recyclerView_search = view.findViewById(R.id.recyclerview_searchbaihat);
        txt_none = view.findViewById(R.id.textviewkocodulieu);
        linearLayout = view.findViewById(R.id.txt1);
        lnls1 = view.findViewById(R.id.lnls1);
        lnls2 = view.findViewById(R.id.lnls2);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar_search);
        toolbar_search.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        // Lắng nghe thay đổi của text
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchTuKhoaBaiHat(query);
                return true;
            }

            @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    });
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void SearchTuKhoaBaiHat(String query){
        DataService dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetSearchBaiHat(query);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat = (ArrayList<BaiHat>) response.body();
                if(mangbaihat.size() >0){
                    searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(),mangbaihat);
                    LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                    recyclerView_search.setLayoutManager(llm);
                    recyclerView_search.setAdapter(searchBaiHatAdapter);
                    //Ẩn hiện các View trong phần Layout
                    txt_none.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    recyclerView_search.setVisibility(View.VISIBLE);
                }else {
                    recyclerView_search.setVisibility(View.GONE);
                    txt_none.setVisibility(View.VISIBLE);
                    lnls1.setVisibility(View.GONE);
                    lnls2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }
}
