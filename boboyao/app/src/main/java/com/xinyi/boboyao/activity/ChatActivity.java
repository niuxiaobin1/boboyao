package com.xinyi.boboyao.activity;

import android.os.Bundle;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.fragment.ChatFragment;

public class ChatActivity extends BaseActivity {

    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        activityInstance = this;
        //get user id or group id
        //use EaseChatFratFragment
        chatFragment = new ChatFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();

    }

}
