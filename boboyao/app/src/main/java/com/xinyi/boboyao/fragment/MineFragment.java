package com.xinyi.boboyao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xinyi.boboyao.BaseFragment;
import com.xinyi.boboyao.R;

/**
 * Created by Niu on 2017/11/7.
 */

public class MineFragment extends BaseFragment {

    private View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        }

        return rootView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolBars = (LinearLayout) rootView.findViewById(R.id.toolBars);
    }
}
