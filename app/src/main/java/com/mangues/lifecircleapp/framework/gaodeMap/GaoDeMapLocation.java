package com.mangues.lifecircleapp.framework.gaodeMap;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.bean.LocationInfo;

/**
 * Created by mangues on 16/8/2.
 */

public class GaoDeMapLocation implements AMapLocationListener {

    private GaoDeLocationListener gaoDeLocationListener;
    private LocationInfo locationInfo = new LocationInfo();

    private static final GaoDeMapLocation gaode = new GaoDeMapLocation();
    public static  GaoDeMapLocation getInstance(){
        return gaode;
    }
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    public GaoDeMapLocation(){
        locationClient = new AMapLocationClient(LifeCircleApplication.getInstance().getApplicationContext());
        locationOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否只定位一次,默认为false
        locationOption.setOnceLocation(true);
        // 设置定位监听
        locationClient.setLocationListener(this);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    public void startLocation(GaoDeLocationListener gaoDeLocationListener){
        this.gaoDeLocationListener = gaoDeLocationListener;
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }



    Handler mHandler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                //开始定位
                case Utils.MSG_LOCATION_START:
//                    tvReult.setText("正在定位...");
                    break;
                // 定位完成
                case Utils.MSG_LOCATION_FINISH:
                    AMapLocation loc = (AMapLocation) msg.obj;
                    String result = Utils.getLocationStr(loc);
                    if(loc.getErrorCode() == 0){  //成功
                        locationInfo.setLatitude(loc.getLatitude());
                        locationInfo.setLongitude(loc.getLongitude());
                        gaoDeLocationListener.location(locationInfo);
                    }else {//失败
                        gaoDeLocationListener.error(loc);
                    }
                    locationClient.stopLocation();
                    break;
                //停止定位
                case Utils.MSG_LOCATION_STOP:
//                    tvReult.setText("定位停止");
                    break;
                default:
                    break;
            }
        };
    };



    /**
     * 定位监听
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (null != aMapLocation) {
            Message msg = mHandler.obtainMessage();
            msg.obj = aMapLocation;
            msg.what = Utils.MSG_LOCATION_FINISH;
            mHandler.sendMessage(msg);
        }
    }
}
