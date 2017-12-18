package com.xinyi.boboyao.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.NetUtils;
import com.xinyi.boboyao.Constant;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.activity.ChatActivity;

import java.util.List;

/**
 * Created by Niu on 2017/8/24.
 */

public class ConversationListFragment extends EaseConversationListFragment implements EMMessageListener {

    private TextView errorText;

    @Override
    protected void initView() {
        super.initView();
        hideTitleBar();
        hideSearchLayout();

    }


    @Override
    public void onStop() {
        super.onStop();
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }


    @Override
    protected List<EMConversation> loadConversationList() {

        List<EMConversation> lic = super.loadConversationList();
        int a=0;
        for (int j = 0; j < 5; j++) {
            a=j;
            boolean exit = false;
            for (int i = 0; i < lic.size(); i++) {
                if (lic.get(i).conversationId().equals("123" + a)) {
                    lic.add(0, lic.remove(i));
                    exit = true;
                    break;
                }
            }

            if (!exit) {

                EMConversation em = EMClient.getInstance().chatManager().getConversation("123" + j, EaseCommonUtils.getConversationType(EaseConstant.CHATTYPE_SINGLE), true);
                if (em.getLastMessage() == null) {
                    EMMessage message = EMMessage.createReceiveMessage(EMMessage.Type.TXT);
                    message.addBody(new EMTextMessageBody("您好，请问您有什么问题？"));
                    message.setMsgTime(System.currentTimeMillis());
                    em.insertMessage(message);

                }
                lic.add(0, em);
            }
        }


        return lic;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);

                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });

        super.setUpView();
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
//            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
//            errorText.setText(R.string.the_current_network);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideSoftKeyboard();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if (tobeDeleteCons.getType() == EMConversation.EMConversationType.GroupChat) {
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.conversationId());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.conversationId(), deleteMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();
        // update unread count
//        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        });
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {

    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageRecalled(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {

    }
}
