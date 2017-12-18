package com.xinyi.boboyao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.fragment.AmusementFragment;
import com.xinyi.boboyao.fragment.BusinessFragment;
import com.xinyi.boboyao.fragment.MessageFragment;
import com.xinyi.boboyao.fragment.MineFragment;
import com.xinyi.boboyao.fragment.ZhuanmiFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.footer_business_tv)
    TextView footer_business_tv;

    @BindView(R.id.footer_message_tv)
    TextView footer_message_tv;

    @BindView(R.id.footer_zhuanmi_tv)
    TextView footer_zhuanmi_tv;

    @BindView(R.id.footer_amusement_tv)
    TextView footer_amusement_tv;

    @BindView(R.id.footer_mine_tv)
    TextView footer_mine_tv;

    private BusinessFragment business_fragment;
    private MessageFragment message_fragment;
    private ZhuanmiFragment zhuanmi_fragment;
    private AmusementFragment amusement_fragment;
    private MineFragment mine_fragment;


    private List<TextView> textViews;
    private static FragmentManager fMgr;

    private int currentShow = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        switchFragment(0);

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.footer_business_tv:
                switchFragment(0);
                break;
            case R.id.footer_message_tv:
                switchFragment(1);
                break;
            case R.id.footer_zhuanmi_tv:
                switchFragment(2);
                break;
            case R.id.footer_amusement_tv:
                switchFragment(3);
                break;
            case R.id.footer_mine_tv:
                switchFragment(4);
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param pos
     */
    private void switchFragment(int pos) {

        if (pos == currentShow) {
            return;
        }
        currentShow = pos;
        startFragment(pos);
        for (int i = 0; i < textViews.size(); i++) {

            if (i == pos) {
                textViews.get(i).setSelected(true);
            } else {
                textViews.get(i).setSelected(false);
            }
        }
    }


    /**
     * fragment切换管理
     *
     * @param i
     */
    public void startFragment(int i) {
        FragmentTransaction ft = fMgr.beginTransaction();
        switch (i) {
            case 0:
                ft.hide(message_fragment).hide(zhuanmi_fragment).hide(amusement_fragment).hide(mine_fragment)
                        .show(business_fragment).commit();
                break;
            case 1:
                ft.hide(business_fragment).hide(zhuanmi_fragment).hide(amusement_fragment).hide(mine_fragment)
                        .show(message_fragment).commit();
                break;
            case 2:
                ft.hide(business_fragment).hide(message_fragment).hide(amusement_fragment).hide(mine_fragment)
                        .show(zhuanmi_fragment).commit();
                break;
            case 3:
                ft.hide(business_fragment).hide(message_fragment).hide(zhuanmi_fragment).hide(mine_fragment)
                        .show(amusement_fragment).commit();
                break;
            case 4:
                ft.hide(business_fragment).hide(message_fragment).hide(zhuanmi_fragment).hide(amusement_fragment)
                        .show(mine_fragment).commit();
                break;

        }
    }

    private void init() {


        fMgr = getSupportFragmentManager();
        textViews = new ArrayList<>();

        textViews.add(footer_business_tv);
        textViews.add(footer_message_tv);
        textViews.add(footer_zhuanmi_tv);
        textViews.add(footer_amusement_tv);
        textViews.add(footer_mine_tv);

        footer_business_tv.setOnClickListener(this);
        footer_message_tv.setOnClickListener(this);
        footer_zhuanmi_tv.setOnClickListener(this);
        footer_amusement_tv.setOnClickListener(this);
        footer_mine_tv.setOnClickListener(this);

        business_fragment = (BusinessFragment) fMgr.findFragmentById(R.id.business_fragment);
        message_fragment = (MessageFragment) fMgr.findFragmentById(R.id.message_fragment);
        zhuanmi_fragment = (ZhuanmiFragment) fMgr.findFragmentById(R.id.zhuanmi_fragment);
        amusement_fragment = (AmusementFragment) fMgr.findFragmentById(R.id.amusement_fragment);
        mine_fragment = (MineFragment) fMgr.findFragmentById(R.id.mine_fragment);


        loginEase("ceshi123", "123456", "TestUser");
    }

    private void loginEase(String currentUsername, String currentPassword, final String nickName) {
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.e("nxb", "login: onSuccess");


                // ** manually load all local groups and conversation
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                // update current user's display name for APNs
                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(
                        nickName);
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }


                // get user's info (this should be get from App's server or 3rd party service)


            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(final int code, final String message) {

                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}
