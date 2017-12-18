package com.xinyi.boboyao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.adapter.TempTaskAdapter;
import com.xinyi.boboyao.decoration.SimplePaddingDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TempTaskActivity extends BaseActivity {

    @BindView(R.id.recyleView)
    RecyclerView recyleView;

    private TempTaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_task);
        initTitle(R.string.zhuanmi_tempTask);
        initRightIcon(R.mipmap.add_icon);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {

        adapter = new TempTaskAdapter(context);
        recyleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyleView.addItemDecoration(new SimplePaddingDecoration(context));
        recyleView.setAdapter(adapter);
    }

    @Override
    protected void onRightClick(View view) {
        super.onRightClick(view);
    }
}
