package com.xinyi.boboyao.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.xinyi.boboyao.BaseFragment;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.activity.AdvActivity;
import com.xinyi.boboyao.activity.LuckpanActivity;
import com.xinyi.boboyao.activity.ShareholderActivity;
import com.xinyi.boboyao.activity.ShouGongActivity;
import com.xinyi.boboyao.activity.TempTaskActivity;
import com.xinyi.boboyao.activity.VipRedPacketActivity;
import com.xinyi.boboyao.adapter.ShouGongAdapter;
import com.xinyi.boboyao.adapter.TempTaskAdapter;
import com.xinyi.boboyao.tools.DensityUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Niu on 2017/11/7.
 */

public class ZhuanmiFragment extends BaseFragment {

    private View rootView;
    @BindView(R.id.banner)
    XBanner banner;

    @BindView(R.id.vipPacket_layout)
    LinearLayout vipPacket_layout;

    @BindView(R.id.tempTask_layout)
    LinearLayout tempTask_layout;

    @BindView(R.id.luckpan_layout)
    LinearLayout luckpan_layout;

    @BindView(R.id.shougong_layout)
    LinearLayout shougong_layout;

    @BindView(R.id.shareholder_layout)
    LinearLayout shareholder_layout;

    @BindView(R.id.readAdv_layout)
    LinearLayout readAdv_layout;

    @BindView(R.id.zhuanmi_invitefirend_ll)
    LinearLayout invitefirend_layout;

    private PopupWindow popupWindow;
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
            rootView = inflater.inflate(R.layout.fragment_zhuanmi, container, false);
        }
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.vipPacket_layout, R.id.tempTask_layout,R.id.luckpan_layout,
            R.id.shougong_layout,R.id.shareholder_layout,R.id.readAdv_layout, R.id.zhuanmi_invitefirend_ll})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent it = null;
        switch (v.getId()) {
            case R.id.vipPacket_layout:
                it = new Intent(getActivity(), VipRedPacketActivity.class);

                break;
            case R.id.tempTask_layout:
                it = new Intent(getActivity(), TempTaskActivity.class);
                break;
            case R.id.luckpan_layout:
                it = new Intent(getActivity(), LuckpanActivity.class);
                break;
            case R.id.shougong_layout:
                it = new Intent(getActivity(), ShouGongActivity.class);
                break;
            case R.id.shareholder_layout:
                it = new Intent(getActivity(), ShareholderActivity.class);
                break;
            case R.id.readAdv_layout:
                it = new Intent(getActivity(), AdvActivity.class);
                break;
            case R.id.zhuanmi_invitefirend_ll:
                showInviteFriendPopup();
                break;
        }
        if (it != null) {
            startActivity(it);
        }
    }

    @Override
    protected void initView() {

        images = new ArrayList<>();
        titles = new ArrayList<>();
//
        toolBars = (LinearLayout) rootView.findViewById(R.id.toolBars);
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



    /**
     * 邀请好友弹窗
     */
    private void showInviteFriendPopup() {

        if (popupWindow == null) {
            View view = LayoutInflater.from(context).inflate(R.layout.invite_friend_popup, null);
            view.findViewById(R.id.close_image).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                    params.alpha = 1f;
                    getActivity().getWindow().setAttributes(params);
                    popupWindow.dismiss();
                }
            });
            int w = DensityUtils.getScreenWidth(context) * 4 / 5;
            int h = (int) (w * 1.423 + DensityUtils.dp2px(getActivity(), 130));
            popupWindow = new PopupWindow(view, w, h);
            popupWindow.setTouchable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        }
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.3f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);


    }
}
