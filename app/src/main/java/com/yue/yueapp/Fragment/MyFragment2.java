package com.yue.yueapp.Fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.yue.yueapp.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


/**
 * Created by Administrator on 2017/2/28.
 */

public class MyFragment2 extends Fragment implements View.OnClickListener {

    private LinearLayout ll_show;
    private Button bt_send;
    private EditText et_input, et_iptext;
    private ScrollView scrollView;

    private final int SET_INPUT_NULL = 0;

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SET_INPUT_NULL:
                    et_input.setText(null);
                    break;
            }
        }
    };

    public MyFragment2() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);

        //简单的布局可以在这里加载，也可以全部写在onActivityCreated里面
        ll_show = (LinearLayout) view.findViewById(R.id.show);
        bt_send = (Button) view.findViewById(R.id.send);
        et_iptext = (EditText) view.findViewById(R.id.toip);
        et_input = (EditText) view.findViewById(R.id.input);
        scrollView = (ScrollView) view.findViewById(R.id.scroll);

        bt_send.setOnClickListener(this);


        return view;
    }

    //核心的动态代码写在这里
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        et_iptext.setFocusable(true);
        et_iptext.setFocusableInTouchMode(true);
        et_iptext.requestFocus();
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
        manager.showSoftInput(et_iptext, 0);

        //给该scrollview设置监听，监听其布局发生变化事件,即监听键盘的动作，然后让scrollview滚到底部
        /*scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
            @Override
            public void onGlobalLayout(){
                //比较Activity根布局与当前布局的大小
                int heightDiff = scrollView.getRootView().getHeight()- scrollView.getHeight();
                if(heightDiff >100){
                    //大小超过100时，一般为显示虚拟键盘事件
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    });
                }
            }
        });*/
    }

    //点击发送后向服务器发送消息
    private void click_bt_send() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Socket socket = null;
                try {
                    //创建一个流套接字并将其连接到指定主机上的指定端口号
                    socket = new Socket("10.129.101.12", 8888);

                    //读取服务器端数据
                    DataInputStream input = new DataInputStream(socket.getInputStream());

                    //向服务器端发送数据
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    String str = et_input.getText().toString();

                    Message message = new Message();
                    message.what = SET_INPUT_NULL;
                    handler.sendMessage(message);

                    String toip = et_iptext.getText().toString();
                    str = "/" + toip + "#" + str ;
                    out.writeUTF(str);

                    final String ret = input.readUTF();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            addText(ll_show, ret);
                        }
                    });
                    System.out.println("服务器端返回过来的是: " + ret);
                    out.close();
                    input.close();
                } catch (Exception e) {
                    System.out.println("客户端异常:" + e.getMessage());
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            System.out.println("客户端 finally 异常:" + e.getMessage());
                        }
                    }
                }
            }
        }).start();
    }

    //添加textview
    private void addText(LinearLayout linear, String text) {
        TextView textView = new TextView(linear.getContext());
        textView.setText(text);
        textView.setBackgroundColor(Color.parseColor("#b9b9b9"));
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(dpTopx(8), dpTopx(8), dpTopx(8), dpTopx(8));
        textView.setLayoutParams(lp);
        linear.addView(textView);
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    //将dp单位换算为px
    private int dpTopx(int dp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.getResources().getDisplayMetrics());
        return px;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send:
                click_bt_send();
                break;
        }
    }
}
