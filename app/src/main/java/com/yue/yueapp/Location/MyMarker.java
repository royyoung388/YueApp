package com.yue.yueapp.Location;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

/**
 * Created by Administrator on 2017/3/12 0012.
 */
public class MyMarker {
    public Marker marker;//地图标注
    public Client client;//用户
    String request;//需求
    TencentMap map;
    public MyMarker(TencentMap tmap) {
        map = tmap;
        marker = null;
    }
    public void setMarker(Client cl, String str) {
        client = cl;
        request = str;
        marker = map.addMarker(new MarkerOptions().position(toPos(cl.getPos())));
    }

    private LatLng toPos(TencentLocation loc) {
        return new LatLng(loc.getLatitude(), loc.getLongitude());
    }
}
