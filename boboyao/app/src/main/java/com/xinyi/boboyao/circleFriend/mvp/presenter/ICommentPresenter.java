package com.xinyi.boboyao.circleFriend.mvp.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xinyi.boboyao.circleFriend.mvp.callback.OnCommentChangeCallback;


/**
 * Created by 大灯泡 on 2016/12/6.
 */

public interface ICommentPresenter {


    /**
     * 添加评论
     */
    void addComment(@NonNull String momentsId,
                    @NonNull String authorId,
                    @Nullable String replyUserId,
                    @NonNull String content,
                    @NonNull OnCommentChangeCallback onCommentChangeCallback);

    void deleteComment(@NonNull String commentid, @NonNull final OnCommentChangeCallback onCommentChangeCallback);
}
