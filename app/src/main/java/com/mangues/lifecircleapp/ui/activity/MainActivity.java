package com.mangues.lifecircleapp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.ui.fragment.RecommendFragment;

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
    }


    @Override
    protected CharSequence getTopTitle() {
        return "首页";
    }


}
