package com.xinyi.boboyao.circleFriend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.razerdp.github.com.common.MomentsType;
import com.razerdp.github.com.common.entity.CommentInfo;
import com.razerdp.github.com.common.entity.LikesInfo;
import com.razerdp.github.com.common.entity.MomentsInfo;
import com.razerdp.github.com.common.entity.UserInfo;
import com.razerdp.github.com.common.manager.LocalHostManager;
import com.razerdp.github.com.common.request.MomentsRequest;
import com.razerdp.github.com.common.router.RouterList;
import com.socks.library.KLog;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.circleFriend.adapter.CircleMomentsAdapter;
import com.xinyi.boboyao.circleFriend.mvp.presenter.impl.MomentPresenter;
import com.xinyi.boboyao.circleFriend.mvp.view.IMomentView;
import com.xinyi.boboyao.circleFriend.viewholder.EmptyMomentsVH;
import com.xinyi.boboyao.circleFriend.viewholder.MultiImageMomentsVH;
import com.xinyi.boboyao.circleFriend.viewholder.TextOnlyMomentsVH;
import com.xinyi.boboyao.circleFriend.viewholder.WebMomentsVH;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import razerdp.github.com.lib.common.entity.ImageInfo;
import razerdp.github.com.lib.helper.PermissionHelper;
import razerdp.github.com.lib.manager.KeyboardControlMnanager;
import razerdp.github.com.lib.network.base.OnResponseListener;
import razerdp.github.com.lib.utils.ToolUtil;
import razerdp.github.com.ui.base.BaseTitleBarActivity;
import razerdp.github.com.ui.helper.PhotoHelper;
import razerdp.github.com.ui.imageloader.ImageLoadMnanger;
import razerdp.github.com.ui.util.UIHelper;
import razerdp.github.com.ui.widget.commentwidget.CommentBox;
import razerdp.github.com.ui.widget.commentwidget.CommentWidget;
import razerdp.github.com.ui.widget.common.TitleBar;
import razerdp.github.com.ui.widget.popup.SelectPhotoMenuPopup;
import razerdp.github.com.ui.widget.pullrecyclerview.CircleRecyclerView;
import razerdp.github.com.ui.widget.pullrecyclerview.interfaces.OnRefreshListener2;


/**
 * 朋友圈主界面
 */
public class FriendCircleActivity extends BaseTitleBarActivity implements OnRefreshListener2, IMomentView, CircleRecyclerView.OnPreDispatchTouchListener {

    private static final int REQUEST_REFRESH = 0x10;
    private static final int REQUEST_LOADMORE = 0x11;


    private CircleRecyclerView circleRecyclerView;
    private CommentBox commentBox;
    private HostViewHolder hostViewHolder;
    private CircleMomentsAdapter adapter;
    private List<MomentsInfo> momentsInfoList;
    //request
    private MomentsRequest momentsRequest;
    private MomentPresenter presenter;

    private CircleViewHelper mViewHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_circle_demo);

        momentsInfoList = new ArrayList<>();
        momentsRequest = new MomentsRequest();
        initView();
        initKeyboardHeightObserver();
    }

    @Override
    public void onHandleIntent(Intent intent) {

    }


    private void initView() {
        if (mViewHelper == null) {
            mViewHelper = new CircleViewHelper(this);
        }
        setTitle("朋友圈");
        setTitleMode(TitleBar.MODE_BOTH);
        setTitleRightIcon(R.drawable.ic_camera);
        setTitleLeftText("发现");
        setTitleLeftIcon(R.drawable.back_left);
        presenter = new MomentPresenter(this);

        hostViewHolder = new HostViewHolder(this);
        circleRecyclerView = (CircleRecyclerView) findViewById(R.id.recycler);
        circleRecyclerView.setOnRefreshListener(this);
        circleRecyclerView.setOnPreDispatchTouchListener(this);
        circleRecyclerView.addHeaderView(hostViewHolder.getView());

        commentBox = (CommentBox) findViewById(R.id.widget_comment);
        commentBox.setOnCommentSendClickListener(onCommentSendClickListener);

        CircleMomentsAdapter.Builder<MomentsInfo> builder = new CircleMomentsAdapter.Builder<>(this);
        builder.addType(EmptyMomentsVH.class, MomentsType.EMPTY_CONTENT, R.layout.moments_empty_content)
                .addType(MultiImageMomentsVH.class, MomentsType.MULTI_IMAGES, R.layout.moments_multi_image)
                .addType(TextOnlyMomentsVH.class, MomentsType.TEXT_ONLY, R.layout.moments_only_text)
                .addType(WebMomentsVH.class, MomentsType.WEB, R.layout.moments_web)
                .setData(momentsInfoList)
                .setPresenter(presenter);
        adapter = builder.build();
        circleRecyclerView.setAdapter(adapter);
        circleRecyclerView.autoRefresh();

    }

    private void initKeyboardHeightObserver() {
        //观察键盘弹出与消退
        KeyboardControlMnanager.observerKeyboardVisibleChange(this, new KeyboardControlMnanager.OnKeyboardStateChangeListener() {
            View anchorView;

            @Override
            public void onKeyboardChange(int keyboardHeight, boolean isVisible) {
                int commentType = commentBox.getCommentType();
                if (isVisible) {
                    //定位评论框到view
                    anchorView = mViewHelper.alignCommentBoxToView(circleRecyclerView, commentBox, commentType);
                } else {
                    //定位到底部
                    commentBox.dismissCommentBox(false);
                    mViewHelper.alignCommentBoxToViewWhenDismiss(circleRecyclerView, commentBox, commentType, anchorView);
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_REFRESH);
        momentsRequest.setCurPage(0);
        momentsRequest.execute();
    }

    @Override
    public void onLoadMore() {
        momentsRequest.setOnResponseListener(momentsRequestCallBack);
        momentsRequest.setRequestType(REQUEST_LOADMORE);
        momentsRequest.execute();
    }


    @Override
    public void onTitleDoubleClick() {
        super.onTitleDoubleClick();
        if (circleRecyclerView != null) {
            int firstVisibleItemPos = circleRecyclerView.findFirstVisibleItemPosition();
            circleRecyclerView.getRecyclerView().smoothScrollToPosition(0);
            if (firstVisibleItemPos > 1) {
                circleRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        circleRecyclerView.autoRefresh();
                    }
                }, 200);
            }
        }
    }

    @Override
    public void onTitleLeftClick() {

        super.onBackPressed();
    }

    @Override
    public void onTitleRightClick() {
        new SelectPhotoMenuPopup(this).setOnSelectPhotoMenuClickListener(new SelectPhotoMenuPopup.OnSelectPhotoMenuClickListener() {
            @Override
            public void onShootClick() {
                PhotoHelper.fromCamera(FriendCircleActivity.this, false);
            }

            @Override
            public void onAlbumClick() {
                ActivityLauncher.startToPhotoSelectActivity(getActivity(), RouterList.PhotoSelectActivity.requestCode);
            }
        }).showPopupWindow();
    }


    @Override
    public boolean onTitleRightLongClick() {
        ActivityLauncher.startToPublishActivityWithResult(this, RouterList.PublishActivity.MODE_TEXT, null, RouterList.PublishActivity.requestCode);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.handleActivityResult(this, requestCode, resultCode, data, new PhotoHelper.PhotoCallback() {
            @Override
            public void onFinish(String filePath) {
                List<ImageInfo> selectedPhotos = new ArrayList<ImageInfo>();
                selectedPhotos.add(new ImageInfo(filePath, null, null, 0, 0));
                ActivityLauncher.startToPublishActivityWithResult(FriendCircleActivity.this,
                        RouterList.PublishActivity.MODE_MULTI,
                        selectedPhotos,
                        RouterList.PublishActivity.requestCode);
            }

            @Override
            public void onError(String msg) {
                UIHelper.ToastMessage(msg);
            }
        });
        if (requestCode == RouterList.PhotoSelectActivity.requestCode && resultCode == RESULT_OK) {
            List<ImageInfo> selectedPhotos = data.getParcelableArrayListExtra(RouterList.PhotoSelectActivity.key_result);
            if (selectedPhotos != null) {
                ActivityLauncher.startToPublishActivityWithResult(this, RouterList.PublishActivity.MODE_MULTI, selectedPhotos, RouterList.PublishActivity.requestCode);
            }
        }

        if (requestCode == RouterList.PublishActivity.requestCode && resultCode == RESULT_OK) {
            circleRecyclerView.autoRefresh();
        }
    }


    //request
    //==============================================
    private OnResponseListener.SimpleResponseListener<List<MomentsInfo>> momentsRequestCallBack = new OnResponseListener.SimpleResponseListener<List<MomentsInfo>>() {
        @Override
        public void onSuccess(List<MomentsInfo> response, int requestType) {
            circleRecyclerView.compelete();
            switch (requestType) {
                case REQUEST_REFRESH:
                    if (!ToolUtil.isListEmpty(response)) {
                        KLog.i("firstMomentid", "第一条动态ID   >>>   " + response.get(0).getMomentid());
                        hostViewHolder.loadHostData(LocalHostManager.INSTANCE.getLocalHostUser());
                        adapter.updateData(response);
                    }

                    break;
                case REQUEST_LOADMORE:
                    adapter.addMore(response);
                    break;
            }
        }

        @Override
        public void onError(BmobException e, int requestType) {
            super.onError(e, requestType);
            circleRecyclerView.compelete();
        }
    };


    @Override
    public boolean onPreTouch(MotionEvent ev) {
        if (commentBox != null && commentBox.isShowing()) {
            commentBox.dismissCommentBox(false);
            return true;
        }
        return false;
    }

    @Override
    public void onLikeChange(int itemPos, List<LikesInfo> likeUserList) {

        MomentsInfo momentsInfo = adapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setLikesList(likeUserList);
            adapter.notifyItemChanged(itemPos);
        }

    }

    @Override
    public void onCommentChange(int itemPos, List<CommentInfo> commentInfoList) {
        MomentsInfo momentsInfo = adapter.findData(itemPos);
        if (momentsInfo != null) {
            momentsInfo.setCommentList(commentInfoList);
            adapter.notifyItemChanged(itemPos);
        }
    }

    @Override
    public void showCommentBox(@Nullable View viewHolderRootView, int itemPos, String momentid, CommentWidget commentWidget) {
        //如果回复评论，commentWidget，viewHolderRootView都不为空
        //如果添加评论，commentWidget==null
        if (commentWidget != null) {
            mViewHelper.setCommentAnchorView(commentWidget);
            if (viewHolderRootView != null) {
                mViewHelper.setViewHolder(viewHolderRootView);
            }
        } else {
            if (viewHolderRootView != null) {
                mViewHelper.setCommentAnchorView(viewHolderRootView);
            }
        }

        mViewHelper.setCommentItemDataPosition(itemPos);
        commentBox.toggleCommentBox(momentid, commentWidget == null ? null : commentWidget.getData(), false);

    }

    @Override
    public void onDeleteMomentsInfo(@NonNull MomentsInfo momentsInfo) {
        int pos = adapter.getDatas().indexOf(momentsInfo);
        if (pos < 0) return;
        adapter.deleteData(pos);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    //=============================================================call back
    private CommentBox.OnCommentSendClickListener onCommentSendClickListener = new CommentBox.OnCommentSendClickListener() {
        @Override
        public void onCommentSendClick(View v, String momentid, String commentAuthorId, String commentContent) {
            if (TextUtils.isEmpty(commentContent)) return;
            int itemPos = mViewHelper.getCommentItemDataPosition();
            if (itemPos < 0 || itemPos > adapter.getItemCount()) return;
            List<CommentInfo> commentInfos = adapter.findData(itemPos).getCommentList();
            presenter.addComment(itemPos, momentid, commentAuthorId, commentContent, commentInfos);
            commentBox.clearDraft();
            commentBox.dismissCommentBox(true);
        }
    };




    private static class HostViewHolder {
        private View rootView;
        private ImageView friend_wall_pic;
        private ImageView friend_avatar;
        private ImageView message_avatar;
        private TextView message_detail;
        private TextView hostid;

        public HostViewHolder(Context context) {
            this.rootView = LayoutInflater.from(context).inflate(R.layout.circle_host_header, null);
            this.hostid = (TextView) rootView.findViewById(R.id.host_id);
            this.friend_wall_pic = (ImageView) rootView.findViewById(R.id.friend_wall_pic);
            this.friend_avatar = (ImageView) rootView.findViewById(R.id.friend_avatar);
            this.message_avatar = (ImageView) rootView.findViewById(R.id.message_avatar);
            this.message_detail = (TextView) rootView.findViewById(R.id.message_detail);
        }

        public void loadHostData(UserInfo hostInfo) {
            if (hostInfo == null) return;
            ImageLoadMnanger.INSTANCE.loadImage(friend_wall_pic, hostInfo.getCover());
            ImageLoadMnanger.INSTANCE.loadImage(friend_avatar, hostInfo.getAvatar());
            hostid.setText("您的测试ID为: " + hostInfo.getUserid() + '\n' + "您的测试用户名为: " + hostInfo.getNick());
        }

        public View getView() {
            return rootView;
        }

    }

}
