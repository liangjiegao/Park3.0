package com.example.superl.park30.UI.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v7.widget.RecyclerView;


import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.UI.view.SpaceItemDecoration;
import com.example.superl.park30.domain.AppContext;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by gdei on 2018/6/21.
 */

public class IndoorParkActivity extends BaseActivity {

    private ArrayList<Integer> carArray;
    private RelativeLayout indoorContent;
    private RecyclerView carShow;   //用于显示停车场车位状况的RecycleView
    private Button selectPosOk;
    private int selectPos = AppContext.getSelectPos();
    private View view;

    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.activity_indoor_park, null);

        initView();
        initData();
        //创建自定义的适配器
        MyCarShowRVAdapter myCarShowRVAdapter = new MyCarShowRVAdapter(this, carArray, new OnPosClickListener() {
            @Override
            public void posClick(int pos, MyCarShowRVAdapter.MyViewHolder holder) {
                if (carArray.get(pos) == 1){
                    Toast.makeText(IndoorParkActivity.this, "该车位已停车或已被预约", Toast.LENGTH_SHORT).show();
                }else {
                    if (selectPos == -1){
                        selectPos = pos;
                        holder.carLoc.setBackgroundResource(R.color.home_navigation_bar4);
                    }else if (pos == selectPos){
                        selectPos = -1;
                        holder.carLoc.setBackgroundResource(R.color.indoor_no_car);
                    }else if (selectPos != -1){
                        Toast.makeText(IndoorParkActivity.this, "一次最多选择一个车位哦", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        carShow = view.findViewById(R.id.rv_car_show);
        carShow.setLayoutManager(new GridLayoutManager(this,8));
        carShow.addItemDecoration(new SpaceItemDecoration(50));
        carShow.setAdapter(myCarShowRVAdapter);



        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        return check(true);
    }

    private void initView(){

        indoorContent = view.findViewById(R.id.rl_indoor);
        carShow = view.findViewById(R.id.rv_car_show);
        selectPosOk = view.findViewById(R.id.pos_select_ok);
        selectPosOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果选择了一个车位
                if (selectPos != -1){
                    AppContext.setSelectPos(selectPos);
                }
                finish();
            }
        });
    }
    private void initData(){
        carArray = new ArrayList<>();
        carArray = AppContext.getCarArray();
    }

    //自定义RecycleView的适配器
    class MyCarShowRVAdapter extends RecyclerView.Adapter<MyCarShowRVAdapter.MyViewHolder>{

        private Context mContext;
        private ArrayList<Integer> mCarStates;
        private OnPosClickListener mListener;
        public MyCarShowRVAdapter(Context context, ArrayList<Integer> carStates, OnPosClickListener listener){
            mContext = context;
            mCarStates = carStates;
            mListener = listener;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_car_loc,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(rootView);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            //如果当前位置信息为0，代表可停车
            if (mCarStates.get(position) == 0){
                holder.carLoc.setBackgroundResource(R.color.indoor_no_car);
            }else {
                holder.carLoc.setBackgroundResource(R.color.indoor_has_car);
            }
            holder.carLoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.posClick(position, holder);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mCarStates.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            private LinearLayout carLoc;

            public MyViewHolder(View itemView) {
                super(itemView);

                carLoc = itemView.findViewById(R.id.ll_car_loc);
            }
        }
    }
    interface OnPosClickListener{

        void posClick(int pos, MyCarShowRVAdapter.MyViewHolder holder);

    }
}
