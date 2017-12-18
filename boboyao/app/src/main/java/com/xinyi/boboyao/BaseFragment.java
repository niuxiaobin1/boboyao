package com.xinyi.boboyao;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xinyi.boboyao.tools.StatusBarUtil;
import com.xinyi.boboyao.tools.SystemBarTintManager;

/**
 * Created by Niu on 2017/3/15.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context context;

    public LinearLayout toolBars;

    @Override
    public abstract View onCreateView(LayoutInflater inflater,
                                      ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity();
        initView();// 加载view
        initData();// 加载数据 data
    }

    private void initToolBar() {

        if (toolBars == null) {
            return;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            toolBars.setFitsSystemWindows(true);
            toolBars.requestApplyInsets();
        } else {
            if (toolBars != null) {
                toolBars.setPadding(toolBars.getPaddingLeft(),
                        new SystemBarTintManager(getActivity()).getStatusBarHeight(),
                        toolBars.getPaddingRight(),
                        toolBars.getPaddingBottom());
            }
        }


    }


    private void unInitToolBar() {

        if (toolBars == null) {
            return;
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            toolBars.setFitsSystemWindows(false);
            toolBars.requestApplyInsets();
        } else {

        }


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            unInitToolBar();
        } else {
            initToolBar();
        }


    }

    protected abstract void initData();


    protected abstract void initView();

    @Override
    public void onClick(View v) {

    }

    protected void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
