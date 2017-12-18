package com.xinyi.boboyao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.xinyi.boboyao.BaseFragment;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.activity.MoneyTreeActivity;
import com.xinyi.boboyao.activity.RedPacketRainActivity;
import com.xinyi.boboyao.activity.TreasureMapActivity;
import com.xinyi.boboyao.tools.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Niu on 2017/11/7.
 */

public class AmusementFragment extends BaseFragment {

    private View rootView;

    @BindView(R.id.banner)
    XBanner banner;

    @BindView(R.id.treasureMap_layout)
    LinearLayout treasureMap_layout;
    @BindView(R.id.redPacketRain_layout)
    LinearLayout redPacketRain_layout;
    @BindView(R.id.moneyTree_layout)
    LinearLayout moneyTree_layout;


    private List<String> images;
    private List<String> titles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_amusement, container, false);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.treasureMap_layout, R.id.redPacketRain_layout, R.id.moneyTree_layout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent it = null;
        switch (v.getId()) {

            case R.id.treasureMap_layout:
                it = new Intent(getActivity(), TreasureMapActivity.class);

                break;
            case R.id.redPacketRain_layout:
                it = new Intent(getActivity(), RedPacketRainActivity.class);
                break;
            case R.id.moneyTree_layout:
                it = new Intent(getActivity(), MoneyTreeActivity.class);
                break;

        }
        startActivity(it);
    }

    @Override
    protected void initView() {

        images = new ArrayList<>();
        titles = new ArrayList<>();
//
        toolBars = (LinearLayout) rootView.findViewById(R.id.toolBars);
//
        banner.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewGroup.LayoutParams pa = banner.getLayoutParams();
                pa.width = DensityUtils.getScreenWidth(getActivity());
                pa.height = (int) (pa.width / 2);
                banner.setLayoutParams(pa);
            }
        });
        initXbanner();

    }


    private void initXbanner() {

        titles.clear();
        images.clear();
        for (int i = 0; i < 3; i++) {
            if (i == 0) {

                images.add(i, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512363271&di=b9e9b97f32e808dad1f5d53af345028e&imgtype=jpg&er=1&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F19%2F71%2F54%2F24Z58PICECz_1024.jpg");
            } else if (i == 1) {
                images.add(i, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511768552861&di=8b3756c31ea23177a9bf1e8bb3867ce5&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F012d3658de20b6a801219c77083959.jpg");

            } else {
                images.add(i, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1511768552860&di=5247ec658d78f8a3bad485f8649ac58a&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01ecc455a707e332f8758bedc15a43.jpg");

            }
            titles.add(i, "");
        }

        // 为XBanner绑定数据
        banner.setData(images, titles);
        // XBanner适配数据
        banner.setmAdapter(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, View view, int position) {
                Glide.with(getActivity()).load(images.get(position)).into((ImageView) view);
            }
        });
        // 设置XBanner的页面切换特效
        banner.setPageTransformer(Transformer.Default);
        // 设置XBanner页面切换的时间，即动画时长
        banner.setPageChangeDuration(1000);
        banner.setmAutoPalyTime(5000);
    }
}
