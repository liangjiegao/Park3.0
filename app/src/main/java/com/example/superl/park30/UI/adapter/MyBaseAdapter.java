package com.example.superl.park30.UI.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.superl.park30.UI.holder.BaseHolder;;
import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/18.
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> list;
    private BaseHolder holder;

    public MyBaseAdapter(ArrayList<T> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder = null;
        if (convertView == null){
            holder = getHolder();
        }else {
            holder = (BaseHolder) convertView.getTag();
        }
        //4、刷新数据
        holder.setData(getItem(position), position);
        return holder.getRootView();
    }

    //返回当前页面的holder的对象，必须由子类实现
    public abstract BaseHolder<T> getHolder();
}
