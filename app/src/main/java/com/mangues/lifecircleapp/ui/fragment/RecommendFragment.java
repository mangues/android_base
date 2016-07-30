package com.mangues.lifecircleapp.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mangues.lifecircleapp.R;

/**
 * Created by mangues on 16/7/12.
 */

public class RecommendFragment extends BaseFragement {

    @Override
    protected View getLayoutView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_recomment, container, false);
        return view;
    }

    @Override
    protected String getTopTitle() {
        return "早上好";
    }
}
