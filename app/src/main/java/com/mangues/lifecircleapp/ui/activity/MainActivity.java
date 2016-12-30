package com.mangues.lifecircleapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.ui.fragment.RecommendFragment;
import com.mangues.mglib.bean.LocationInfo;
import com.mangues.mglib.framework.gaodeMap.GaoDeLocationListener;
import com.mangues.mglib.framework.gaodeMap.GaoDeMapLocation;

import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private Fragment recommentFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initTab();
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
