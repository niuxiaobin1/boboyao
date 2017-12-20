package com.razerdp.github.com.common.request;

import cn.bmob.v3.exception.BmobException;
import razerdp.github.com.lib.network.base.OnResponseListener;
import razerdp.github.com.ui.util.UIHelper;

/**
 * Created by 大灯泡 on 2016/10/28.
 */

public abstract class SimpleResponseListener<T> implements OnResponseListener<T> {

    @Override
    public void onStart(int requestType) {

    }

    @Override
    public void onError(BmobException e, int requestType) {
        UIHelper.ToastMessage(e.getMessage());
        e.printStackTrace();
    }
}
