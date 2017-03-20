package com.yue.yueapp.Fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.yue.yueapp.Location.Client;
import com.yue.yueapp.Location.DemoLocationSource;
import com.yue.yueapp.Location.MyMarker;
import com.yue.yueapp.R;

import java.util.ArrayList;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment1 extends Fragment implements TencentLocationListener {

    LatLng curr_pos;
    MapView mMapView = null;
    DemoLocationSource demoLocationSource = null;
    TencentMap tencentMap;
    ArrayList<MyMarker> markerList;
    private TencentLocationManager mLocationManager;
    public MyMarker findMyMarker(Marker marker) {
        for (MyMarker mk:markerList)
            if (mk.marker.equals(marker))
                return mk;
        return null;
    }
    Client cl;

    public MyFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        //简单的布局可以在这里加载，也可以全部写在onActivityCreated里面

        mMapView = (MapView) view.findViewById(R.id.map);

        return view;
    }

    //核心的动态代码写在这里
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        markerList = new ArrayList<MyMarker>();
        tencentMap = mMapView.getMap();
        tencentMap.setMapType(TencentMap.MAP_TYPE_NORMAL);

        /*
        cl = new Client("ttr", "abcd");
        cl.setAge(200);
        cl.setID(0);
        ArrayList<String> arr = new ArrayList<>();
        arr.add("hobby1");
        arr.add("hobby2");
        cl.setLikes(arr);
        cl.setMajor("Software Engineering");
        cl.setName("TTR");
        cl.setSex(0);
        TencentLocation loc = new TencentLocation() {
            @Override
            public String getProvider() {
                return null;
            }

            @Override
            public double getLatitude() {
                return 30;
            }

            @Override
            public double getLongitude() {
                return 114;
            }

            @Override
            public double getAltitude() {
                return 0;
            }

            @Override
            public float getAccuracy() {
                return 0;
            }

            @Override
            public String getName() {
                return "Name_";
            }

            @Override
            public String getAddress() {
                return "Address_";
            }

            @Override
            public String getNation() {
                return "China";
            }

            @Override
            public String getProvince() {
                return null;
            }

            @Override
            public String getCity() {
                return "WUHAN";
            }

            @Override
            public String getDistrict() {
                return null;
            }

            @Override
            public String getTown() {
                return null;
            }

            @Override
            public String getVillage() {
                return null;
            }

            @Override
            public String getStreet() {
                return null;
            }

            @Override
            public String getStreetNo() {
                return null;
            }

            @Override
            public Integer getAreaStat() {
                return null;
            }

            @Override
            public List<TencentPoi> getPoiList() {
                return null;
            }

            @Override
            public float getBearing() {
                return 0;
            }

            @Override
            public float getSpeed() {
                return 0;
            }

            @Override
            public long getTime() {
                return 0;
            }

            @Override
            public long getElapsedRealtime() {
                return 0;
            }

            @Override
            public int getGPSRssi() {
                return 0;
            }

            @Override
            public double getDirection() {
                return 0;
            }

            @Override
            public String getCityCode() {
                return null;
            }

            @Override
            public String getIndoorBuildingId() {
                return null;
            }

            @Override
            public String getIndoorBuildingFloor() {
                return null;
            }

            @Override
            public int getIndoorLocationType() {
                return 0;
            }

            @Override
            public int getCoordinateType() {
                return 0;
            }

            @Override
            public int isMockGps() {
                return 0;
            }

            @Override
            public Bundle getExtra() {
                return null;
            }
        };
        cl.setPos(loc);
        addNewRequest(cl,"Hello world!");

        */
        UiSettings uiSettings = tencentMap.getUiSettings();
        uiSettings.setCompassEnabled(true);
        demoLocationSource = new DemoLocationSource(mMapView.getContext(), tencentMap);
        tencentMap.setLocationSource(demoLocationSource);
        mLocationManager = TencentLocationManager.getInstance(mMapView.getContext());
        mLocationManager = TencentLocationManager.getInstance(mMapView.getContext());
        TencentLocationRequest request = TencentLocationRequest.create().setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME).setInterval(0).setAllowDirection(true);
        mLocationManager.requestLocationUpdates(request, this);


        TencentMap.InfoWindowAdapter infoWindowAdapter = new TencentMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindowPressState(Marker arg0) {
                return null;
            }

            //返回View为信息窗自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoWindow(final Marker arg0) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = LayoutInflater.from(mMapView.getContext());
                View view = inflater.inflate(R.layout.markerlayout, null);
                ImageView img_pic = (ImageView) view.findViewById(R.id.img_pic);
                img_pic.setImageBitmap(toRoundBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.img)));
                MyMarker marker = findMyMarker(arg0);
                if (marker != null)
                    showMarkerDialog(marker);
                return view;
            }

            //返回View为信息窗内容自定义样式，返回null时为默认信息窗样式
            @Override
            public View getInfoContents(Marker arg0) {
                // TODO Auto-generated method stub
                return null;
            }

        };

        tencentMap.setInfoWindowAdapter(infoWindowAdapter);
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        mMapView.onStop();
    }

    /*@Override
    public void onRestart() {
        // TODO Auto-generated method stub
        super.onResume();
        mMapView.onRestart();
    }*/

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mMapView.onDestroy();
    }

    public void addNewRequest(Client client, String request) {
        MyMarker marker = new MyMarker(tencentMap);
        marker.setMarker(client, request);
        markerList.add(marker);
    }
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        return output;
    }
    private void showMarkerDialog(MyMarker myMarker) {
        String[] SexStr = {"男","女"};
        Client client = myMarker.client;
        TencentLocation location = client.getPos();
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialoglayout, null);
        final Dialog dialog = new Dialog(mMapView.getContext(), R.style.MaterialDialogSheet);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        TextView tv_username = (TextView)view.findViewById(R.id.username),
                tv_name = (TextView)view.findViewById(R.id.name),
                tv_age = (TextView)view.findViewById(R.id.age),
                tv_sex = (TextView)view.findViewById(R.id.sex),
                tv_major = (TextView)view.findViewById(R.id.major),
                tv_position = (TextView)view.findViewById(R.id.position);
        tv_username.setText(client.getUsername());
        tv_name.setText(client.getName());
        tv_age.setText(client.getAge() + "");
        tv_sex.setText(SexStr[client.getSex()]);
        tv_major.setText(client.getMajor());
        tv_position.setText("位置:" + location.getCity() + "," + location.getName() + "," + location.getAddress());
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        //   dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    public void onLocationChanged(TencentLocation location, int error,String reason) {

        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                    pos, //新的中心点坐标
                    19,  //新的缩放级别
                    45f, //俯仰角 0~45° (垂直地图时为0)
                    45f)); //偏航角 0~360° (正北方为0)
            tencentMap.moveCamera(camUpdate);
            //    final Marker marker = tencentMap.addMarker(new MarkerOptions().
            //           position(pos));

        }
    }
    @Override
    public void onStatusUpdate(String arg0, int arg1, String arg2) {
        // TODO Auto-generated method stub

    }
}
