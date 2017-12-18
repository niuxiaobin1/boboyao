package com.xinyi.boboyao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;

import butterknife.ButterKnife;

public class MoneyTreeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_tree);
        ButterKnife.bind(this);
        initTitle(R.string.amusement_moneyTree);
        initRightIcon(R.mipmap.setting);
    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
    }
}
