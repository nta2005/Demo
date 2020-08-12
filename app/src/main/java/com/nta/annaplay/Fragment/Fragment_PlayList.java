package com.nta.annaplay.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nta.annaplay.Activity.BaiHatActivity;
import com.nta.annaplay.Activity.PlayListActivity;
import com.nta.annaplay.Adapter.PlayListAdapter;
import com.nta.annaplay.Model.PlayList;
import com.nta.annaplay.R;
import com.nta.annaplay.Service.APIService;
import com.nta.annaplay.Service.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_PlayList extends Fragment {
    View view;
    ListView lvplaylist;
    TextView txttitleplaylist,txtviewxemthemplaylist;
    PlayListAdapter playlistAdapter;
    ArrayList<PlayList> mangplaylist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist,container,false);
        lvplaylist = view.findViewById(R.id.listviewplaylist);
        txttitleplaylist = view.findViewById(R.id.textviewtitleplaylist);
        txtviewxemthemplaylist = view.findViewById(R.id.textviewviewmoreplaylist);
        GetData();
        txtviewxemthemplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlayListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    //Xử lý dữ liệu:
    private void GetData() {
        DataService dataservice = APIService.getService();
        Call<List<PlayList>> callback = dataservice.GetPlaylistCurrentDay();
        //Lắng nghe dữ liệu trả về:
        callback.enqueue(new Callback<List<PlayList>>() {
            //Lắng nghe cho việc dữ liệu trả về thành công:
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
                mangplaylist = (ArrayList<PlayList>) response.body();
                playlistAdapter = new PlayListAdapter(getActivity(),android.R.layout.simple_list_item_1,mangplaylist);
                lvplaylist.setAdapter(playlistAdapter);
                //Căn chỉnh lại kích thước playlist:
                setListViewHeightBasedOnChildren(lvplaylist);
                lvplaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), BaiHatActivity.class);
                        intent.putExtra("itemplaylist",mangplaylist.get(position));
                        startActivity(intent);
                    }
                });
            }
            //Lắng nghe cho việc dữ liệu trả về thất bại:
            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }
    // Code căn chỉnh lại kích thước của playlist:
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
