package com.yue.yueapp.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yue.yueapp.R;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment1 extends Fragment {

    public MyFragment1() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        //简单的布局可以在这里加载，也可以全部写在onActivityCreated里面

        TextView txt_content = (TextView) view.findViewById(R.id.text2);
        txt_content.setText("第一个Fragment");
        return view;
    }

    //核心的动态代码写在这里
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
