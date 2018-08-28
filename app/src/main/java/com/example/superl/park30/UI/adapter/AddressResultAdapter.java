package com.example.superl.park30.UI.adapter;

import com.example.superl.park30.UI.holder.AddressResultHolder;
import com.example.superl.park30.UI.holder.BaseHolder;
import com.example.superl.park30.domain.Address;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AddressResultAdapter extends MyBaseAdapter<Address> {

    public AddressResultAdapter(ArrayList<Address> list) {
        super(list);
    }

    @Override
    public BaseHolder<Address> getHolder() {
        AddressResultHolder holder = new AddressResultHolder();
        return holder;
    }
}
