package com.mangues.lifecircleapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.mangues.lifecircleapp.R;
import com.mangues.lifecircleapp.base.basemvp.BasePresenter;
import com.mangues.lifecircleapp.bean.LocationInfo;
import com.mangues.lifecircleapp.framework.gaodeMap.GaoDeLocationListener;
import com.mangues.lifecircleapp.framework.gaodeMap.GaoDeMapLocation;
import com.mangues.lifecircleapp.log.MLogger;
import com.mangues.lifecircleapp.mvpview.CircleMvpView;
import com.mangues.lifecircleapp.presenter.CirclePresenter;
import com.mangues.lifecircleapp.view.MyListView;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mangues on 16/7/12.
 */

public class RecommendFragment extends BaseFragement implements CircleMvpView{

    @Bind(R.id.list)
    MyListView list;

    @Inject
    CirclePresenter circlePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getComponent().inject(this);
        View view = inflater.inflate(R.layout.fragment_recomment, container, false);
        return view;
    }

    @Override
    protected String getTopTitle() {
        return "早上好";
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GaoDeMapLocation.getInstance().startLocation(new GaoDeLocationListener() {
            @Override
            public void location(LocationInfo locationInfo) {
                circlePresenter.circleList(locationInfo);

            }

            @Override
            public void error(AMapLocation location) {
                showToast(location.getErrorInfo());
            }
        });
    }


    @Override
    protected BasePresenter[] initPresenters() {
        return new BasePresenter[]{circlePresenter};
    }


    @Override
    public void onSuccess(Object t) {
        List list = (List)t;
        MLogger.json(list);
    }

    @Override
    public void onError(Object error) {
        MLogger.i((String)error);
    }



}
