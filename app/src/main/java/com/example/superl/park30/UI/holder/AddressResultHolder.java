package com.example.superl.park30.UI.holder;

import android.view.View;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.domain.Address;
import com.example.superl.park30.util.UIUtils;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AddressResultHolder extends BaseHolder<Address> {

    private View view;
    private TextView name;
    private TextView city;

    @Override
    public View initView() {
        view = View.inflate(UIUtils.getContext(), R.layout.address_result_item, null);
        name = view.findViewById(R.id.tv_address_name);
        city = view.findViewById(R.id.tv_address_city);
        return view;
    }

    @Override
    public void refreshView(Address data, int pos) {
        name.setText(data.getName());
        city.setText(data.getCity());
    }
}
