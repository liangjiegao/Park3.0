package com.example.superl.park30.UI.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.superl.park30.ParkMsg;
import com.example.superl.park30.R;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/18.
 */

public class HistoryHolder extends BaseHolder<ParkMsg> {

    private View view;
    private TextView parkName;

    @Override
    public View initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.history_item, null);
        parkName = view.findViewById(R.id.tv_park_name);
        return view;

    }

    @Override
    public void refreshView(ParkMsg data, int pos) {
        parkName.setText(data.getName());
    }
}
