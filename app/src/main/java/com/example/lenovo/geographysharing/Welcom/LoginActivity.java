package com.example.lenovo.geographysharing.Welcom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.geographysharing.Home.HomeActivity;
import com.example.lenovo.geographysharing.R;

public class LoginActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponent();

    }
    private void initializeComponent() {

//        //标题栏
//        ActionBar actionBar = getSupportActionBar();
//        //隐藏标题栏
//        if (actionBar != null) {
//            actionBar.hide();
//        }
        //隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //控件实例化
        Button login = (Button) findViewById(R.id.login_button);
        Button forget = (Button) findViewById(R.id.login_forget);
        Button create = (Button) findViewById(R.id.login_create);
        //控件的监听绑定
        login.setOnClickListener(this);
        forget.setOnClickListener(this);
        create.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.login_forget) {
            Toast.makeText(this, "忘记密码", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.login_create) {
            Toast.makeText(this, "注册新用户", Toast.LENGTH_SHORT);
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
    public static void launchLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        //防止多次实例调用
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

}
