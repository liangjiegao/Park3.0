package com.example.superl.park30.UI.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.adapter.MyBaseAdapter;
import com.example.superl.park30.UI.holder.BaseHolder;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AboutAuthorActivity extends BaseActivity {

    private View view;
    private ListView authorMsgListView;
    private ArrayList<String> values;

    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.about_author, null);
        initView();
        return view;
    }
    private void initView(){
        authorMsgListView = view.findViewById(R.id.lv_author_msg);
        authorMsgListView.setAdapter(new MyAdapter(values));
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        values = new ArrayList<>();
        values.add("易泊停车中国");
        values.add("15521168615");
        values.add("1445808283@qq.com");
        values.add("1445808283@qq.com");
        values.add("1445808283@qq.com");
        values.add("1445808283@qq.com");

        return check(true);
    }
    class MyAdapter extends MyBaseAdapter<String>{


        public MyAdapter(ArrayList<String> list) {
            super(list);
        }

        @Override
        public BaseHolder<String> getHolder() {
            MyHolder holder = new MyHolder();
            return holder;
        }
    }
    class MyHolder extends BaseHolder<String>{

        private TextView tvKey;
        private TextView tvValue;
        private String[] keys = {"微信公众号", "联系电话", "电子邮件", "政府及公共事务", "官方网站", "广告合作"};
        private int[] icons = {R.mipmap.wechar, R.mipmap.tel, R.mipmap.email, R.mipmap.government, R.mipmap.official, R.mipmap.advertising, };
        private ImageView ivIcon;

        @Override
        public View initView() {
            View view = View.inflate(UIUtils.getContext(), R.layout.author_msg_item, null);
            tvKey = view.findViewById(R.id.tv_key);
            tvValue = view.findViewById(R.id.tv_value);
            ivIcon = view.findViewById(R.id.img);
            return view;
        }

        @Override
        public void refreshView(String data, int pos) {
            tvKey.setText(keys[pos]);
            tvValue.setText(data);
            ivIcon.setImageResource(icons[pos]);
        }
    }
}
