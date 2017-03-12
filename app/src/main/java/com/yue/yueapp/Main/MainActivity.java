package com.yue.yueapp.Main;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yue.yueapp.Fragment.MyFragment1;
import com.yue.yueapp.Fragment.MyFragment2;
import com.yue.yueapp.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Objects
    private TextView menu1, menu2;

    //Fragment Object
    private MyFragment1 fg1;
    private MyFragment2 fg2;

    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu_main);//设置右上角的填充菜单
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ab_search:
                        Toast.makeText(MainActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        fManager = getFragmentManager();
        bindViews();
        menu1.performClick();   //模拟一次点击，既进去后选择第一项
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 為了讓 Toolbar 的 Menu 有作用，這邊的程式不可以拿掉
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        menu1 = (TextView) findViewById(R.id.menu1);
        menu2 = (TextView) findViewById(R.id.menu2);

        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        menu1.setSelected(false);
        menu2.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
    }



    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.menu1:
                setSelected();
                menu1.setSelected(true);
                if(fg1 == null){
                    fg1 = new MyFragment1();
                    fTransaction.add(R.id.frame,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.menu2:
                setSelected();
                menu2.setSelected(true);
                if(fg2 == null){
                    fg2 = new MyFragment2();
                    fTransaction.add(R.id.frame,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
        }
        fTransaction.commit();
    }
}