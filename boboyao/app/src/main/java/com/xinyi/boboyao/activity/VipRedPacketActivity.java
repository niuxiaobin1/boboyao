package com.xinyi.boboyao.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.tools.DensityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VipRedPacketActivity extends BaseActivity {

    @BindView(R.id.redPacket_layout)
    RelativeLayout redPacket_layout;

    @BindView(R.id.rob_packet_tv)
    TextView rob_packet_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_red_packet);
        initTitle(R.string.activity_vip_title);
        ButterKnife.bind(VipRedPacketActivity.this);
        initViews();

    }

    private void initViews() {
        redPacket_layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = redPacket_layout.getLayoutParams();
                params.width = DensityUtils.getScreenWidth(context) / 2;
                params.height = DensityUtils.getScreenWidth(context) * 3 / 4;
                redPacket_layout.setLayoutParams(params);
            }
        });


    }
}
