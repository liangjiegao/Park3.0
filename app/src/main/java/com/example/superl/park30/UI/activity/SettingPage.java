package com.example.superl.park30.UI.activity;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.superl.park30.R;
import com.example.superl.park30.UI.view.LoadingPage;
import com.example.superl.park30.util.LogUtils;
import com.example.superl.park30.util.UIUtils;

import java.util.ArrayList;

/**
 * Created by SuperL on 2018/8/17.
 */

public class SettingPage extends BaseActivity {

    private static final int ADDRESS = 0;
    private static final int VERSION = 1;
    private static final int AUTHOR = 2;
    private static final int USER_KNOWN = 3;

    private ArrayList<String> settingItems = new ArrayList<>();
    private View view;
    private ListView listView;

    private static final String PACKAGE_NAME_YINYONGBAO1 = "com.tencent.android.qqdownloader";
    private static final String PACKAGE_NAME_YINYONGBAO2 = "com.bbk.appstore";

    @Override
    public View setPage() {
        view = View.inflate(UIUtils.getContext(), R.layout.setting_page, null);
        initView();
        initListener();
        return view;
    }
    private void initView(){
        listView = view.findViewById(R.id.lv_setting);
        listView.setAdapter(new MySettingAdapter(settingItems));
    }
    private void initListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case ADDRESS:
                        intent = new Intent(SettingPage.this, AddressSettingActivity.class);
                        intent.putExtra("title", "常用地址");
                        break;
                    case VERSION:
                        launchapp(UIUtils.getContext());
                        break;
                    case AUTHOR:
                        intent = new Intent(SettingPage.this, AboutAuthorActivity.class);
                        intent.putExtra("title", "关于我们");
                        break;
                    case USER_KNOWN:
                        intent = new Intent(SettingPage.this, UserAgreementActivity.class);
                        intent.putExtra("title", "用户协议");
                        break;
                }
                if (intent != null){
                    startActivity(intent);
                }
            }
        });
    }
    //跳转到其他app的方法
    private void launchapp(Context context){
        //判断当前是否有要跳转的app
        if (isAppInstalled(context, PACKAGE_NAME_YINYONGBAO1) || isAppInstalled(context, PACKAGE_NAME_YINYONGBAO2)){
            //如果有该这两个应用市场应用
            //context.startActivity(context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME_YINYONGBAO1));
            goToMarket(context, "com.mobike.mobikeapp");

        }else {
            //如果没有
            LogUtils.i("没有该应用");
        }
    }

    /**
     * 跳转到 应用市场的指定app的下载页面
     * @param context
     * @param packageName   要显示下载页面的app的包名 这里暂时用 膜拜单车的 com.mobike.mobikeapp
     */
    public static void goToMarket(Context context, String packageName) {
        Uri uri = Uri.parse("market://details?id=" + packageName);
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            //若要指定启动的应用， 添加以下代码
            //goToMarket.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
            goToMarket.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断当前手机上是否有安装指定应用
     * @param context
     * @param packageName
     * @return
     */
    private boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getPackageInfo(packageName, 0);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public LoadingPage.ResultState onLoad() {

        settingItems.add("常用地址");
        settingItems.add("检查新版本");
        settingItems.add("关于我们");
        settingItems.add("用户协议");

        return check(true);
    }

}
class MySettingAdapter extends BaseAdapter {
    ArrayList<String> mList;
    public MySettingAdapter(ArrayList<String> list){
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

            convertView = View.inflate(UIUtils.getContext(), R.layout.setting_item, null);
        }

        SettingHolder holder = SettingHolder.getMyHolder(convertView);
        holder.textView.setText(mList.get(position));
        return convertView;
    }
}
//封装Holder
class SettingHolder {
    TextView textView;
    private SettingHolder(View view){
        textView = view.findViewById(R.id.distance);
        view.setTag(this);

    }
    public static SettingHolder getMyHolder(View view){
        SettingHolder holder = (SettingHolder) view.getTag();
        if (holder == null){
            holder = new SettingHolder(view);
        }
        return holder;
    }
}