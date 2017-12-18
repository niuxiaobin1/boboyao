package com.xinyi.boboyao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.tools.DensityUtils;
import com.xinyi.boboyao.views.luck.LuckPanLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LuckpanActivity extends BaseActivity implements LuckPanLayout.AnimationEndListener {

    @BindView(R.id.ll)
    LinearLayout ll;
    @BindView(R.id.imageview)
    ImageView imageview;

    @BindView(R.id.luckpan_layout)
    LuckPanLayout luckPanLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luckpan);
        initTitle(R.string.zhuanmi_luckpan);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {

        luckPanLayout.setAnimationEndListener(this);

        ll.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int w = DensityUtils.getScreenWidth(context);
                int h = DensityUtils.getScreenHeight(context);
                ViewGroup.LayoutParams params = ll.getLayoutParams();
                params.width = w;
                params.height = h;
                ll.setLayoutParams(params);
            }
        });

        imageview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams params = imageview.getLayoutParams();
                int w = DensityUtils.getScreenWidth(context) - DensityUtils.dp2px(context, 40);
                params.width = w;
                params.height = (int) (w / 2.33425);
                imageview.setLayoutParams(params);
            }
        });

    }

    @Override
    public void endAnimation(int position) {
        if (position == 7) {

            Toast.makeText(this, "恭喜你获得" + getResources().getStringArray(R.array.names)[0], Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "恭喜你获得" + getResources().getStringArray(R.array.names)[position+1], Toast.LENGTH_SHORT).show();

        }
    }

    public void rotation(View view) {
        Log.e("nxb", "rotation");
        luckPanLayout.rotate(-1, 100);

    }
}
