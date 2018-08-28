package com.example.superl.park30.UI.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.example.superl.park30.UI.adapter.PopWindowAdapter;
import com.example.superl.park30.domain.AppContext;
import com.example.superl.park30.ParkMsg;
import com.example.superl.park30.R;
import com.example.superl.park30.domain.UserMsg;
import com.example.superl.park30.domain.UserState;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.StringUtils;
import com.example.superl.park30.util.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SuperL on 2018/8/14.
 */

public class MainActivity extends Activity implements View.OnClickListener, AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMapLocationListener, INaviInfoCallback, AMap.OnMapTouchListener {

    private ArrayList<String> menuItems = new ArrayList<>();

    public static final int RESULT_CODE = 10086;

    private ListView mMenuItem;

    private FrameLayout mFLMenu;
    private View menuView;
    private ImageView userHead;
    private TextView userNum;
    private TextView mSetting;
    private TextView mUserHelper;
    private TextView mEsc;
    private RelativeLayout user;


    private AMap aMap;
    private MapView mapView;

    private EditText parkInput;
    private ImageView mSeek;
    private ImageView navigation;

    private ArrayList<ParkMsg> selectPark;
    private boolean isClickItem = false;
    //--------------定位-----------------
    private AMapLocationClient mLocClient = null;
    private AMapLocationClientOption mOption = null;
    private LatLng myLatLng;
    //定位小蓝点
    private MyLocationStyle locationStyle;
    //定位按钮
    private UiSettings mUiSetting;

    //模拟停车场定位信息
    private Map<String, ParkMsg> mParkLocs;
    private String input;
    private PopupWindow popupWindow;
    private ListView listView;

    private CameraUpdate update;
    private PopWindowAdapter popWindowAdapter;
    private Marker curShowWindowMarker;
    private Map<String, Marker> markerMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        loadMenu();
        mapView.onCreate(savedInstanceState);
        initData();

    }
    private void initView(){

        mapView = findViewById(R.id.map_view);
        aMap = mapView.getMap();
        aMap.setOnMarkerClickListener(this);
        aMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        aMap.setOnInfoWindowClickListener(this);

        mFLMenu = findViewById(R.id.fl_menu);
        parkInput = findViewById(R.id.et_seek_park);
        mSeek = findViewById(R.id.seek_park);
        mSeek.setOnClickListener(this);
        navigation = findViewById(R.id.navigation);
        navigation.setOnClickListener(this);
    }

    private void initData() {

        if (menuView != null){
            mFLMenu.addView(menuView);
        }

        mLocClient = new AMapLocationClient(this);
        mLocClient.setLocationListener(this);

        mOption = new AMapLocationClientOption();
        //设置定位场景
        mOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);

        //设置定位模式，高精度 或 低耗能 或 无网络
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位时间间隔 或 单次定位
        mOption.setInterval(1000);
        //设置定位超时
        mOption.setHttpTimeOut(20000);

        if (null != mLocClient){
            mLocClient.setLocationOption(mOption);
            //确保场景启动，先停止再启动
            mLocClient.stopLocation();
            mLocClient.startLocation();
        }

        //定位小蓝点
        locationStyle = new MyLocationStyle();
        locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW);//只定位一次。
        //locationStyle.interval(10000);
        aMap.setMyLocationStyle(locationStyle);
        aMap.setMyLocationEnabled(true);

        //定位按钮
        mUiSetting = aMap.getUiSettings();
//        aMap.setLocationSource(this);
        mUiSetting.setMyLocationButtonEnabled(true);
//        aMap.setMyLocationEnabled(true);
//
//        locationStyle.showMyLocation(true);
        mParkLocs = new HashMap<>();
        mParkLocs.put("大润发停车场",new ParkMsg("大润发停车场",22.997852,112.558846,10,10,5,8));
        AppContext.userApplyPark = "大润发停车场";
        mParkLocs.put("沃尔玛停车场",new ParkMsg("沃尔玛停车场",22.958952,112.508446,10,10,4,11));
        mParkLocs.put("罗勒停车场",new ParkMsg("罗勒停车场",22.955052,112.508746,10,10,3,9.5));
        mParkLocs.put("山口停车场",new ParkMsg("山口停车场",22.946652,112.508646,10,10,4,10));
        mParkLocs.put("广东第二师范学院学院停车场",new ParkMsg("广东第二师范学院学院停车场",22.953552,112.508346,10,10,3,13));
        mParkLocs.put("中山大学停车场",new ParkMsg("中山大学停车场",22.953452,112.508746,10,10,5,8.5));
        mParkLocs.put("天河体育中心停车场",new ParkMsg("天河体育中心停车场",22.952952,112.508146,10,10,2,9));
        AppContext.setmParkLocs(mParkLocs);


        initListView();
        selectPark = new ArrayList<>();
        popWindowAdapter = new PopWindowAdapter(selectPark, UIUtils.getContext());
        listView.setAdapter(popWindowAdapter);

        //监听输入框文字改变
        parkInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                //如果不是在下拉列表中选择停车场时的文字改变，代表手动输入
                if (!isClickItem){
                    selectPark.clear();//将以前添加进来的搜索内容清空
                    //获取输入框信息
                    input = parkInput.getText().toString();
                    for (String parkName : mParkLocs.keySet()) {
                        if (!StringUtils.isEmpty(input) && parkName.contains(input)){  //在集合中查询是否包含该有输入关键字的停车场
                            selectPark.add(mParkLocs.get(parkName));    //将符合条件的停车场添加进集合中
                        }else if (StringUtils.isEmpty(input)){
                            break;
                        }
                    }
                    popWindowAdapter.notifyDataSetChanged();
                    //显示下拉列表
                    showPopupWindow();


                }else {
                    isClickItem = false;
                }
            }
        });
    }
    private void showPopupWindow(){
        //下拉列表最多显示10条信息
        int height = selectPark.size() > 10 ? 10 * 80 :selectPark.size()*80;
        LogUtils.i("selectPark  = "+selectPark.size());
        LogUtils.i("height = "+height);
        if (popupWindow == null){
            popupWindow = new PopupWindow(listView, parkInput.getWidth(),height);
        }
        popupWindow.setHeight(height);
        //popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable()); // 设置空的背景, 响应点击事件
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(parkInput,0,-5);


    }
    /**
     * 初始化下拉列表ListView
     */
    private void initListView(){
        listView = new ListView(UIUtils.getContext());
        listView.setBackgroundColor(Color.WHITE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isClickItem = true;
                input = selectPark.get(position).getName();
                parkInput.setText(selectPark.get(position).getName());
                popupWindow.dismiss();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()){
            case R.id.seek_park:
                //根据输入的正确地址 定位到该位置
                ParkMsg parkMsg = AppContext.getmParkLocs().get(parkInput.getText().toString());
                if (parkMsg == null){
                    Toast.makeText(UIUtils.getContext(),"无法找到“"+parkInput.getText()+"”",Toast.LENGTH_SHORT).show();
                }else{
                    for (String markerTitle : markerMap.keySet()) {
                        if (markerMap.get(markerTitle).getTitle().equals(parkMsg.getName())){
                            curShowWindowMarker = markerMap.get(markerTitle);

                        }
                    }
                    //获取搜索出来的停车场的经纬度
                    LatLng latLng = new LatLng(parkMsg.getLatitude(), parkMsg.getLongitude());
                    update = CameraUpdateFactory.changeLatLng(latLng);
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.5f),2000, new MyCancelableCallback());
                    //aMap.moveCamera(update);
                    //执行相应的动画来定位
                    /*update = CameraUpdateFactory.zoomTo(12);
                    aMap.animateCamera(update, 1000,new MyCancelableCallback());
                    Message msg = new Message();
                    msg.obj = latLng;
                    msg.what = 0x123;
                    handler.sendMessageDelayed(msg,1000);*/
                }
                break;
            case R.id.navigation:
                Poi start = new Poi("我的位置", myLatLng, "");
                LogUtils.i("name = " + input);
                double lat = 0;
                double lon = 0;
                //获取终点位置的经纬度， 将其封装为一个LatLng作为结束位置启动导航组件
                if ( AppContext.getmParkLocs().get(input) != null){
                    lat = AppContext.getmParkLocs().get(input).getLatitude();
                    lon = AppContext.getmParkLocs().get(input).getLongitude();
                }
                LatLng endLatLng = new LatLng(lat, lon);
                Poi end = new Poi(input, endLatLng, "");
                startNativ(start, end);
                break;
            case R.id.user:
                intent = new Intent(MainActivity.this, UserMsgPage.class);
                intent.putExtra("title", "个人信息");

                startActivityForResult(intent,RESULT_CODE);
                return;
            case R.id.tv_setting:
                intent = new Intent(MainActivity.this, SettingPage.class);
                intent.putExtra("title", "设置");
                break;
            case R.id.user_teach:
                intent = new Intent(MainActivity.this, UserHelper.class);
                intent.putExtra("title", "用户指南");
                break;
            case R.id.esc:
                System.exit(0);
                break;
            default:break;
        }
        if (intent != null){
            //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            startActivity(intent);
        }
    }
    //就收设置用户信息后返回的修改数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtils.i("requestCode = " + requestCode);
        if (resultCode == RESULT_CODE && data != null){
            //获取头像图片路径， 并且读取出来设置在userHead上
            String imgHeadPath = data.getStringExtra(UserMsgPage.SET_HEAD);
            LogUtils.i("imgHeadPath = " + imgHeadPath);
            Glide.with(this).load(new File(imgHeadPath)).into(userHead);
        }
    }



    /**
     * 显示附近的停车场定位，用Marker来标记
     */
    private void showParkLoc(){
        MarkerOptions markerOptions = new MarkerOptions();
        View markView = View.inflate(UIUtils.getContext(), R.layout.marker_view, null);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(markView);
        ParkMsg parkMsg;
        markerMap = new HashMap<>();    //用于存放全部marker
        for (String parkName: mParkLocs.keySet()) {
            parkMsg = mParkLocs.get(parkName);
            markerOptions.position(new LatLng(parkMsg.getLatitude(), parkMsg.getLongitude()));
            LogUtils.i(parkMsg.getName());
            markerOptions.title(parkMsg.getName());
            markerOptions.icon(bitmapDescriptor);
            //options.setFlat(true);//设置marker平贴地图效果
            Marker marker =  aMap.addMarker(markerOptions);
            markerMap.put(parkName,marker);
        }
        aMap.setOnMapTouchListener(this);
    }
    @Override
    public boolean onMarkerClick(Marker marker) {
        LogUtils.i("click");
        curShowWindowMarker = marker;
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = new Intent(this, OrderingPage.class);
        intent.putExtra("parkName",marker.getTitle());
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            if (aMapLocation.getErrorCode() == 0){
                double lat  = aMapLocation.getLatitude();
                double lon = aMapLocation.getLongitude();

                myLatLng = new LatLng(lat,lon);
                CameraUpdate update = CameraUpdateFactory.changeLatLng(myLatLng);
                aMap.moveCamera(update);
                update =  CameraUpdateFactory.zoomTo(15);
                aMap.moveCamera(update);
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
        showParkLoc();
    }

    //启动导航组件
    private void startNativ(Poi start, Poi end){

        AmapNaviPage.getInstance().showRouteActivity
                (this,new AmapNaviParams(null,null,end, AmapNaviType.DRIVER),this);
    }
    //当地图其他位置被点击时隐藏信息窗口
    @Override
    public void onTouch(MotionEvent motionEvent) {
        //当地图其他位置被点击时隐藏信息窗口
        if (aMap != null && curShowWindowMarker != null){
            if (curShowWindowMarker.isInfoWindowShown()){
                curShowWindowMarker.hideInfoWindow();
            }
        }
    }


    //自定义InfoWindow
    class MyInfoWindowAdapter implements AMap.InfoWindowAdapter{

        private View infoWindow;

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            if (infoWindow == null){
                infoWindow = LayoutInflater.from(UIUtils.getContext())
                        .inflate(R.layout.z_info_window,null);
            }
            render(marker, infoWindow); //修改infoWindow的信息
            return infoWindow;
        }

        /**
         * 修改InfoWindow信息的方法
         * @param marker
         * @param view
         */
        private void render(Marker marker, View view) {
            ParkMsg parkMsg = mParkLocs.get( marker.getTitle());
            TextView parkName = view.findViewById(R.id.info_window_park_name);
            parkName.setText(parkMsg.getName());
            int[] startId = {R.id.grade1, R.id.grade2, R.id.grade3, R.id.grade4, R.id.grade5};
            ImageView startImg;
            for (int i = 0; i < 5; i++) {
                startImg = view.findViewById(startId[i]);
                if (i < parkMsg.getGrade()){
                    startImg.setImageResource(R.mipmap.start1);
                }else {
                    startImg.setImageResource(R.mipmap.start0);
                }
            }
        }
    }



    /**
     * 加载菜单
     */
    private void loadMenu(){
        if (UserState.getUserCurrentState() == UserState.CurrentUserState.NOLOGIN){
            //当前用户处于未登录状态
            //模拟登录
            //UserMsg.setId(123456) ;
            //UserState.setCurrentUserState(UserState.CurrentUserState.LOGIN);
        }else if (UserState.getUserCurrentState() == UserState.CurrentUserState.LOGIN){
            //当前用户处于已经登录状态
            //创建菜单View
            menuView = View.inflate(this, R.layout.menu_layout, null);
            //初始化menu布局
            menuInitView(menuView);


            //添加模拟数据
            menuItems.add("正在预约");
            menuItems.add("历史记录");

            mMenuItem = menuView.findViewById(R.id.lv_menu_item);
            //添加Adapter
            MyAdapter adapter = new MyAdapter(menuItems);
            mMenuItem.setAdapter(adapter);
            mMenuItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = null;
                    switch (position){
                        case 0:
                            intent = new Intent(MainActivity.this, OrderingPage.class);
                            intent.putExtra("title", "当前预约");
                            break;
                        case 1:
                            intent = new Intent(MainActivity.this, OrderedPage.class);
                            intent.putExtra("title", "历史记录");
                            break;
                            default:break;
                    }
                    if (intent != null){
                        startActivity(intent);
                    }
                }
            });
        }

    }

    /**
     * 初始化菜单的布局
     * @param view
     */
    public void menuInitView(View view){
        user = menuView.findViewById(R.id.user);
        user.setOnClickListener(this);
        mSetting = view.findViewById(R.id.tv_setting);
        mSetting.setOnClickListener(this);
        mUserHelper = view.findViewById(R.id.user_teach);
        mUserHelper.setOnClickListener(this);
        mEsc = view.findViewById(R.id.esc);
        mEsc.setOnClickListener(this);

        userHead = menuView.findViewById(R.id.iv_head);
        //设置菜单栏头像
        Glide.with(this).load(new File(UserMsg.getmImgPath())).into(userHead);
        userNum = menuView.findViewById(R.id.tv_phone_num);
    }

    class MyCancelableCallback implements AMap.CancelableCallback{

        @Override
        public void onFinish() {
            LogUtils.i("移动结束");
            curShowWindowMarker.showInfoWindow();
        }

        @Override
        public void onCancel() {

        }
    }
    /*Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123: //改变当前定位
                    update = CameraUpdateFactory.changeLatLng((LatLng) msg.obj);
                    aMap.animateCamera(update,1000, new MyCancelableCallback());

                    this.sendEmptyMessageDelayed(0x234,1000);
                    break;
                case 0x234:
                    update = CameraUpdateFactory.zoomTo(15);
                    aMap.animateCamera(update,1000, new MyCancelableCallback());

            }

        }
    };*/

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }
}
class MyAdapter extends BaseAdapter{

    private int[] imgId = {R.mipmap.order, R.mipmap.history};

    ArrayList<String> mList;
    public MyAdapter(ArrayList<String> list){
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            Log.i("tag", "UIUtils.getContext() : " + UIUtils.getContext());

            convertView = View.inflate(UIUtils.getContext(), R.layout.menu_item, null);
        }

        MyHolder holder = MyHolder.getMyHolder(convertView);
        holder.tvOrder.setText(mList.get(position));
        holder.ivOrder.setImageResource(imgId[position]);
        return convertView;
    }
}
//封装Holder
   class MyHolder {
    ImageView ivOrder;
    TextView tvOrder;
    private MyHolder(View view){
        ivOrder = view.findViewById(R.id.iv_order);
        tvOrder = view.findViewById(R.id.tv_order);
        view.setTag(this);

    }
    public static MyHolder getMyHolder(View view){
        MyHolder holder = (MyHolder) view.getTag();
        if (holder == null){
            holder = new MyHolder(view);
        }
        return holder;
    }

}