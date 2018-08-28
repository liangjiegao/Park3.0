package com.example.superl.park30.UI.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.superl.park30.ParkMsg;
import com.example.superl.park30.R;
import com.example.superl.park30.UI.adapter.MyBaseAdapter;
import com.example.superl.park30.UI.holder.BaseHolder;
import com.example.superl.park30.UI.holder.HistoryHolder;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/17.
 * 历史记录
 */

public class OrderedPage extends BaseActivity {

    //模拟数据
    private ArrayList<ParkMsg> parkMsgs = new ArrayList<>();

    private static final String TAG = "OrderedPage";
    private View view;
    //加载成功回调该方法
    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.ordered_page, null);

        ListView historyListView = view.findViewById(R.id.history_list);
        //添加模拟数据
        parkMsgs.add(new ParkMsg("大润发停车场",22.997852,112.558846,10,10,5,8));
        parkMsgs.add(new ParkMsg("沃尔玛停车场",22.958952,112.508446,10,10,4,11));
        historyListView.setAdapter(new HistoryAdapter(parkMsgs));

        return view;
    }
    //在子线程中执行， 在网络加载数据
    @Override
    public LoadingPage.ResultState onLoad() {

        return check(view != null);
    }

    class HistoryAdapter extends MyBaseAdapter<ParkMsg>{

        public HistoryAdapter(ArrayList<ParkMsg> list) {
            super(list);
        }

        @Override
        public BaseHolder<ParkMsg> getHolder() {
            HistoryHolder holder = new HistoryHolder();
            return holder;
        }
    }

}
