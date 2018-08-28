package com.example.superl.park30.domain;

/**
 * Created by SuperL on 2018/8/17.
 */

public class UserMsg {

    private static String mUserName;
    private static String mTrueName;
    private static String mId;
    private static String mPassword;
    private static String mImgUrl;
    private static String mImgPath = "/data/data/com.example.superl.park30/cache/image_select/20180822_091042.png";
    private static String check = "未认证";
    private static String sex = "未填写";
    private static String weChet = "未绑定";
    private static String QQ = "未绑定";
    private static Address address1;
    private static Address address2;

    public UserMsg(String trueName,String mUserName, String password, String id){
        this.mTrueName = trueName;
        this.mUserName = mUserName;
        this.mPassword = password;
        this.mId = id;
    }
    public void setUserName(String userName) {
        mUserName = userName;
    }

    public static void setId(String id) {
        mId = id;
    }

    public void setImgUrl(String imgUrl) {
        mPassword = imgUrl;
    }

    public void setPassword(String password) {
        mImgUrl = password;
    }

    public static void setCheck(String check) {
        UserMsg.check = check;
    }

    public static void setWeChet(String weChet) {
        UserMsg.weChet = weChet;
    }

    public static void setQQ(String QQ) {
        UserMsg.QQ = QQ;
    }

    public static void setSex(String sex) {
        UserMsg.sex = sex;
    }

    public static void setmTrueName(String mTrueName) {
        UserMsg.mTrueName = mTrueName;
    }

    public static void setAddress1(Address address1) {
        UserMsg.address1 = address1;
    }

    public static void setAddress2(Address address2) {
        UserMsg.address2 = address2;
    }

    public static void setmImgPath(String mImgPath) {
        UserMsg.mImgPath = mImgPath;
    }

    public static String getUserName() {
        return mUserName;
    }

    public static String getId() {
        return mId;
    }

    public static String getImgUrl() {
        return mImgUrl;
    }

    public static String getPassword() {
        return mPassword;
    }

    public static String getCheck() {
        return check;
    }

    public static String getQQ() {
        return QQ;
    }

    public static String getWeChet() {
        return weChet;
    }

    public static String getSex() {
        return sex;
    }

    public static String getmTrueName() {
        return mTrueName;
    }

    public static Address getAddress1() {
        return address1;
    }

    public static Address getAddress2() {
        return address2;
    }

    public static String getmImgPath() {
        return mImgPath;
    }
}
