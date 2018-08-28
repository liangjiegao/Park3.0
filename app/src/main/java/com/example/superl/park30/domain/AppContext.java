package com.example.superl.park30.domain;

import com.example.superl.park30.ParkMsg;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by SuperL on 2018/7/15.
 */

public class AppContext {

    //初始化停车场的停车状况
    static {
        mCarArray = new ArrayList<>();
        buildArray("0010010111001100010010101001010011010100000101010101010");
    }
    /**
     * 建立车位数组， 用于提供显示依据
     * @param carMsg
     */
    private static void buildArray(String carMsg){
        int isCar;
        for (int i = 0; i < carMsg.length(); i++) {
            isCar = Integer.parseInt(carMsg.substring(i,i+1));
            mCarArray.add(i,isCar);
        }
    }
    private static int selectPos = -1;
    //模拟停车场定位信息
    private static Map<String, ParkMsg> mParkLocs;

    //判断从停车场详情返回时是回到 定位 还是 导航
    public static boolean backToLoc = true;
    //当前选择的停车场名称
    private static String selectPark;
    //当前账号预约的停车场名称
    public static String userApplyPark;
    //停车场停车情况
    private static ArrayList<Integer> mCarArray;

    public static ArrayList<Integer> getCarArray() {
        return mCarArray;
    }

    public static void setCarArray(ArrayList<Integer> carArray) {
        mCarArray = carArray;
    }

    public static Map<String, ParkMsg> getmParkLocs() {
        return mParkLocs;
    }

    public static void setmParkLocs(Map<String, ParkMsg> parkLocs) {
        mParkLocs = parkLocs;
    }

    public static String getSelectPark() {
        return selectPark;
    }

    public static void setSelectPark(String selectPark) {
        AppContext.selectPark = selectPark;
    }

    public static int getSelectPos() {
        return selectPos;
    }

    public static void setSelectPos(int selectPos) {
        AppContext.selectPos = selectPos;
    }
}
