package com.xinyi.boboyao.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.MyApplication;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.views.meteor.MeteorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedPacketRainActivity extends BaseActivity {

    @BindView(R.id.meteorView)
    MeteorView meteorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet_rain);
        initTitle(R.string.amusement_redPacketRain);
        initRightIcon(R.mipmap.add_icon);
        ButterKnife.bind(this);

        initXandY();

    }


    int[] location = new int[2];//获取View的坐标

    private void initXandY() {
        meteorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int[] temp = new int[2];
                meteorView.getLocationInWindow(temp);

                if (temp[0] != location[0] || temp[1] != location[1]) {
                    location[0] = temp[0];
                    location[1] = temp[1];
                    MyApplication.SCREEN_WIDTH = location[0] + meteorView.getWidth();
                    MyApplication.SCREEN_HEIGHT = location[1] + meteorView.getHeight();

                }


            }
        });
    }


    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
        meteorView.rainStart();
    }
}
