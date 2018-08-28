package com.example.superl.park30.UI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.superl.park30.ParkMsg;
import com.example.superl.park30.R;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/18.
 */

public class PopWindowAdapter extends BaseAdapter {

    private ArrayList<ParkMsg> mSelect;
    private Context mContext;
    private TextView text;

    public PopWindowAdapter(ArrayList<ParkMsg> select, Context context){
        mSelect = select;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mSelect.size();
    }

    @Override
    public Object getItem(int position) {
        return mSelect.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.z_seek_park_list_item,parent,false);
        }else {
            view = convertView;
        }
        text = view.findViewById(R.id.select_item_name);
        text.setText(mSelect.get(position).getName()+"");
        return view;

    }
}
