package com.xinyi.boboyao.circleFriend.mvp.presenter;


import com.xinyi.boboyao.circleFriend.mvp.callback.OnLikeChangeCallback;

/**
 * Created by 大灯泡 on 2016/12/6.
 */

public interface ILikePresenter {


    /**
     * 添加点赞
     */
    void addLike(String momentid, OnLikeChangeCallback onLikeChangeCallback);

    /**
     * 移除点赞
     */
    void unLike(String momentid, OnLikeChangeCallback onLikeChangeCallback);
}
