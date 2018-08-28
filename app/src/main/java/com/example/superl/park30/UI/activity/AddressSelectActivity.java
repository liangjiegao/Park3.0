package com.example.superl.park30.UI.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.adapter.AddressResultAdapter;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.domain.Address;
import com.example.superl.park30.manager.AddressSelectManager;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.StringUtils;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AddressSelectActivity extends BaseActivity {

    private View view;
    private ListView result;
    private ArrayList<Address> addresses;
    private ArrayList<Address> seekAdd;
    private LinearLayout llTip;
    private EditText evInput;
    //变量
    private String input;
    private AddressResultAdapter adapter;
    private TextView tvUnDo;

    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.address_select, null);
        mTopLayout.setVisibility(View.GONE);
        initView();
        initData();
        initListener();
        return view;
    }
    private void initView(){
        result = view.findViewById(R.id.lv_address_result);
        llTip = view.findViewById(R.id.ll_result_tip);
        tvUnDo = view.findViewById(R.id.tv_undo);
        evInput = view.findViewById(R.id.ev_address_input);
    }

    private void initData(){
        seekAdd = new ArrayList<>();
        adapter = new AddressResultAdapter(seekAdd);
        result.setAdapter(adapter);

    }

    private void initListener(){
        evInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                seekAdd.clear();
                input = evInput.getText().toString();
                for (Address address : addresses) {
                    String name = address.getName();
                    if (!StringUtils.isEmpty(input) && name.contains(input)){
                        seekAdd.add(address);
                    }else if (StringUtils.isEmpty(input)){
                        seekAdd.clear();
                    }
                }
                adapter.notifyDataSetChanged();
                if (!seekAdd.isEmpty()){
                    llTip.setVisibility(View.VISIBLE);
                }else {
                    llTip.setVisibility(View.GONE);
                }

            }
        });
        tvUnDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressSelectManager manager = AddressSelectManager.getAddressSelectManager();
                AddressSelectActivity.this.listener = manager.listener;
                if (manager != null){
                    listener.onItemSelect(seekAdd.get(position), getIntent().getIntExtra("enter", -1));
                }
                finish();
            }
        });
    }
    private AddressSelectManager.OnItemSelectListener listener;
    @Override
    public LoadingPage.ResultState onLoad() {
        addresses = new ArrayList<>();
        addresses.add(new Address("天河体育中心", "天河区"));
        addresses.add(new Address("广东第二师范学院", "花都区"));
        addresses.add(new Address("荔湾人家", "荔湾区"));
        addresses.add(new Address("长隆欢乐世界", "番禺区"));
        addresses.add(new Address("白云山", "白云区"));
        addresses.add(new Address("从化广场", "从化区"));
        addresses.add(new Address("广州塔", "海珠区"));
        addresses.add(new Address("福田广场", "福田区"));
        addresses.add(new Address("旺角广场", "旺角"));
        addresses.add(new Address("大润发", "端州区"));
        addresses.add(new Address("越秀山体育场", "越秀区"));
        return check(true);
    }
    //一个静态的单例类， 观察者模式， 用于回调listView选择的Item
    public static class AddressSelectManager {

        private AddressSelectManager.OnItemSelectListener listener;
        private static AddressSelectManager manager = new AddressSelectManager();
        private AddressSelectManager(){

        }
        public static AddressSelectManager getAddressSelectManager(){
            return manager;
        }
        public void registerObserver(AddressSelectManager.OnItemSelectListener listener){
            this.listener = listener;
        }

        public interface OnItemSelectListener{
            void onItemSelect(Address address, int enter);
        }
    }

}
