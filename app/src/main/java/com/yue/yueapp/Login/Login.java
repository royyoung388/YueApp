package com.yue.yueapp.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yue.yueapp.Main.MainActivity;
import com.yue.yueapp.R;

/**
 * Created by Administrator on 2017/3/12.
 */

public class Login extends AppCompatActivity{

    private EditText name, pwd;
    private Button login;
    private String in_name, in_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        bindView();
    }

    //绑定View并获取输入
    private void bindView() {
        name = (EditText) findViewById(R.id.name);
        pwd = (EditText) findViewById(R.id.pwd);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_correct();
            }
        });
    }

    //判断登录是否正确
    private void login_correct() {
        //获取输入
        in_name = name.getText().toString();
        in_pwd = pwd.getText().toString();

        if (in_name.equals("123") && in_pwd.equals("123")) {
            startActivity(new Intent(Login.this, MainActivity.class));
        } else {
            Toast.makeText(Login.this, "用户名或密码错误\n请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

}
