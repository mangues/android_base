package com.mangues.mglib.framework.gaodeMap;

import com.amap.api.location.AMapLocation;
import com.mangues.mglib.bean.LocationInfo;

/**
 * Created by mangues on 16/8/2.
 */

public interface GaoDeLocationListener {
    void location(LocationInfo locationInfo);
    void error(AMapLocation location);
}
