package com.yue.yueapp.Location;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;

public class DemoLocationSource implements LocationSource {

    private Context mContext;
    private OnLocationChangedListener mChangedListener;
    private TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    private TencentMap tencentMap;
    public DemoLocationSource(Context context, TencentMap tMap) {
        tencentMap = tMap;
        mContext = context;
        locationManager = TencentLocationManager.getInstance(mContext);
    }

    @Override
    public void activate(OnLocationChangedListener arg0) {
        mChangedListener = arg0;
    }

    @Override
    public void deactivate() {
        // TODO Auto-generated method stub
        mContext = null;
        locationManager = null;
        locationRequest = null;
        mChangedListener = null;

    }

    public void onPause() {

    }

    public void onResume() {

    }

}
