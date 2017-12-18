package com.xinyi.boboyao.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinyi.boboyao.BaseFragment;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.adapter.BusinessContentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Niu on 2017/11/7.
 */

public class BusinessFragment extends BaseFragment {

    private View rootView;

    @BindView(R.id.sort_by_distance_tv)
    TextView sort_by_distance_tv;

    @BindView(R.id.sort_by_praise_tv)
    TextView sort_by_praise_tv;

    @BindView(R.id.sort_by_bestOffer_tv)
    TextView sort_by_bestOffer_tv;


    @BindView(R.id.sort_by_mostPopular_tv)
    TextView sort_by_mostPopular_tv;

    @BindView(R.id.sort_by_raffle_tv)
    TextView sort_by_raffle_tv;

    @BindView(R.id.business_content_recyleView)
    RecyclerView business_content_recyleView;

    private List<TextView> sortTextViews;

    private BusinessContentAdapter businessContentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_business, container, false);
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
        sortTextViews = new ArrayList<>();
        sortTextViews.add(sort_by_distance_tv);
        sortTextViews.add(sort_by_praise_tv);
        sortTextViews.add(sort_by_bestOffer_tv);
        sortTextViews.add(sort_by_mostPopular_tv);
        sortTextViews.add(sort_by_raffle_tv);

        sort_by_distance_tv.setOnClickListener(this);
        sort_by_praise_tv.setOnClickListener(this);
        sort_by_bestOffer_tv.setOnClickListener(this);
        sort_by_mostPopular_tv.setOnClickListener(this);
        sort_by_raffle_tv.setOnClickListener(this);

        businessContentAdapter = new BusinessContentAdapter(getActivity());
        business_content_recyleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        business_content_recyleView.setAdapter(businessContentAdapter);

        sortSelectWhich(0);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.sort_by_distance_tv:
                sortSelectWhich(0);
                break;
            case R.id.sort_by_praise_tv:
                sortSelectWhich(1);
                break;
            case R.id.sort_by_bestOffer_tv:
                sortSelectWhich(2);
                break;
            case R.id.sort_by_mostPopular_tv:
                sortSelectWhich(3);
                break;
            case R.id.sort_by_raffle_tv:
                sortSelectWhich(4);
                break;
        }
    }


    private void sortSelectWhich(int pos) {

        for (int i = 0; i < sortTextViews.size(); i++) {
            if (pos == i) {
                sortTextViews.get(i).setSelected(true);
            } else {
                sortTextViews.get(i).setSelected(false);
            }
        }
    }
}
