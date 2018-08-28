package com.example.superl.park30.manager;

import com.example.superl.park30.domain.Address;

/**
 * Created by SuperL on 2018/8/21.
 */

public class AddressSelectManager {

    private OnItemSelectListener listener;
    private static AddressSelectManager manager = new AddressSelectManager();
    private AddressSelectManager(){

    }
    public static AddressSelectManager getAddressSelectManager(){
        return manager;
    }
    public void registerObserver(OnItemSelectListener listener){
        this.listener = listener;
    }

    public interface OnItemSelectListener{
        void onItemSelect(Address address);
    }

}
