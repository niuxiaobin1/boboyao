package com.xinyi.boboyao.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.xinyi.boboyao.BaseFragment;
import com.xinyi.boboyao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Niu on 2017/11/7.
 */

public class MessageFragment extends BaseFragment {

    private View rootView;

    @BindView(R.id.conversation_ll)
    LinearLayout mConversationLayout;
    @BindView(R.id.scrollView)
    ScrollView scrollView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_message, container, false);
        }
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        toolBars = (LinearLayout) rootView.findViewById(R.id.toolBars);
        getChildFragmentManager().beginTransaction().add(R.id.conversation_ll, new ConversationListFragment()).commit();
//        initUp();
    }


    public void initUp() {
        toolBars.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 50);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            initUp();
        }
    }
}
