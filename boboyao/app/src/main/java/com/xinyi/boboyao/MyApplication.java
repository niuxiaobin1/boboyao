package com.xinyi.boboyao;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.razerdp.github.com.common.manager.LocalHostManager;
import com.xinyi.boboyao.circleFriend.config.Define;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import razerdp.github.com.lib.api.AppContext;
import razerdp.github.com.lib.helper.AppFileHelper;
import razerdp.github.com.lib.manager.localphoto.LocalPhotoManager;

/**
 * Created by Niu on 2017/11/23.
 */

public class MyApplication extends Application {


    public static int SCREEN_WIDTH = 0;
    public static int SCREEN_HEIGHT = 0;

    public static Context applicationContext;
    private static MyApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * nickname for current user, the nickname instead of ID be shown when user receive notification from APNs
     */
    public static String currentUserNick = "";

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();

        applicationContext = this;
        instance = this;

        //init demo helper
        AppHelper.getInstance().init(applicationContext);

        initCircleApp();
    }


    private void initCircleApp(){
        AppContext.initARouter();
        initBmob();
        initLocalHostInfo();
        AppFileHelper.initStoryPath();
        LocalPhotoManager.INSTANCE.registerContentObserver(null);
    }


    private void initBmob() {
        BmobConfig config = new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId(Define.BMOB_APPID)
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(15)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024 * 1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(1800)
                .build();
        Bmob.initialize(config);
    }

    private void initLocalHostInfo() {
        LocalHostManager.INSTANCE.init();
    }


    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
