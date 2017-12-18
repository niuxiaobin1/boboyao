package com.xinyi.boboyao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;

import butterknife.ButterKnife;

public class ShareholderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareholder);
        ButterKnife.bind(this);
        initTitle(R.string.zhuanmi_1Shareholder);

    }
}
