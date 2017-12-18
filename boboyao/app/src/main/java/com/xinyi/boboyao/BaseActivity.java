package com.xinyi.boboyao;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyi.boboyao.tools.StatusBarUtil;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        context = BaseActivity.this;
        StatusBarUtil.transparencyBar(BaseActivity.this);
        AppManager.addActivity(BaseActivity.this);
        //把所有的activity全部添加在栈里，在需要清除所有页面的地方调用AppExit()方法即可。
    }

    protected void showMessage(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showMessageLong(String msg) {
        Toast.makeText(BaseActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {

    }

    protected void initTitle(int res) {
        ImageView imageView = (ImageView) findViewById(R.id.back_image);
        TextView textView = (TextView) findViewById(R.id.title_tv);
        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        if (textView != null) {
            textView.setText(res);
        }
    }


    protected void initRightIcon(int res) {
        ImageView right_image = (ImageView) findViewById(R.id.right_image);

        right_image.setImageResource(res);
        if (right_image != null) {
            right_image.setVisibility(View.VISIBLE);
            right_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRightClick(view);
                }
            });
        }
    }

    protected void onRightClick(View view) {

    }
}
