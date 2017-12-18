package com.xinyi.boboyao.activity;

import android.os.Bundle;
import android.view.View;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;

import butterknife.ButterKnife;

public class RedPacketRainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet_rain);
        initTitle(R.string.amusement_redPacketRain);
        initRightIcon(R.mipmap.add_icon);
        ButterKnife.bind(this);
    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
    }
}
