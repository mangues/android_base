package com.mangues.lifecircleapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.data.cache.SecureSharedPreferences;
import com.mangues.lifecircleapp.data.enjine.GlobalVariables;
import com.mangues.lifecircleapp.model.User;
import com.mangues.lifecircleapp.model.UserRes;
import com.mangues.lifecircleapp.ui.fragment.RecommendFragment;
import com.mangues.mglib.bean.LocationInfo;
import com.mangues.mglib.framework.gaodeMap.GaoDeLocationListener;
import com.mangues.mglib.framework.gaodeMap.GaoDeMapLocation;
import com.mangues.mglib.util.apkupdate.ApkUpdate;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private Fragment recommentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTab();

        User user = new User();
        UserRes userRes = new UserRes();
        userRes.setUser(user);

        SecureSharedPreferences.putString("lll","dddddd");
        user.setName(SecureSharedPreferences.getString("lll"));

        GlobalVariables.getInstance().setUserRes(userRes);
        String namr = GlobalVariables.getInstance().getUserRes().getUser().getName();
        showToast(namr);


        ApkUpdate.getInstance(MainActivity.this).checkApkVersion("https://app.xzssmk.com/xzsmk/","https://app.xzssmk.com/xzsmk/apkVersion/getApkVersionById",true);


    }

    //初始化frament
    private void initTab(){
        recommentFragment = new RecommendFragment();
//        fragments = new Fragment[] {courseFragment, findingFragment, mineFragment };
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.frame_content, recommentFragment).show(recommentFragment).commit();
//                add(R.id.frame_content, recommentFragment).hide(recommentFragment).


        GaoDeMapLocation.getInstance(mContext).startLocation(new GaoDeLocationListener() {
            @Override
            public void location(LocationInfo locationInfo) {
                showToast(locationInfo.getLatitude()+"");
            }

            @Override
            public void error(AMapLocation location) {

            }
        });
    }


    @Override
    protected CharSequence getTopTitle() {
        return "首页";
    }


}
