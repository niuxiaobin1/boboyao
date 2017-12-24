package com.xinyi.boboyao.views.meteor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.xinyi.boboyao.MyApplication;
import com.xinyi.boboyao.R;
import com.xinyi.boboyao.tools.DensityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 绘画View
 * author True Lies
 * Created by Administrator on 2016/6/11.
 */
public class MeteorView extends SurfaceView
        implements
        SurfaceHolder.Callback {
    private Context context;
    private SurfaceHolder mSurfaceHolder = null;
    private boolean isThreadOpen = false;
    private Bitmap mMeteorBmp = null;
    private List<MeteorAddressModule> mMeteorAddressModuleArray = new ArrayList<MeteorAddressModule>();

    private Random mRandom = new Random();
    private Paint mPaint = new Paint();

    public MeteorView(Context context) {
        this(context, null);
    }

    public MeteorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeteorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mContext) {
        this.context=mContext;
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        MyApplication.SCREEN_WIDTH = displayMetrics.widthPixels;
        MyApplication.SCREEN_HEIGHT = displayMetrics.heightPixels;


        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);//事件添加

    }

    public boolean isRun;

    public void rainStart() {
        isRun = true;
    }

    public void rainFinish() {
        isRun = false;
    }

    /**
     * 生成一个新的metor
     */
    private void initMeteorAddressMoudle() {
        //300:xml里面控件的高度。因为是45度降落，高可以直接做运算
        float mX = mRandom.nextInt(MyApplication.SCREEN_WIDTH+ DensityUtils.dp2px(context,300));
        while (Math.abs(mMeteorAddressModuleArray.get(mMeteorAddressModuleArray.size()-1).getmX()-mX)<100){
            mX = mRandom.nextInt(MyApplication.SCREEN_WIDTH+ DensityUtils.dp2px(context,300));
        }
//        float mY = mRandom.nextInt(500);
        float mY = 0;
        MeteorAddressModule meteorAddressModule = new MeteorAddressModule();
        meteorAddressModule.setmX(mX);
        meteorAddressModule.setmY(mY);
        mMeteorAddressModuleArray.add(meteorAddressModule);
    }


    private class DrawThread extends Thread {
        private SurfaceHolder mSurfaceHolder;


        public DrawThread(SurfaceHolder holder) {
            this.mSurfaceHolder = holder;

            for (int i = 0; i < 20; i++) {
                initMeteorAddressMoudle();
            }
        }

        @Override
        public void run() {
            Canvas mCanvas = null;
            while (true) {

                if (!isRun) {
                    continue;
                }

                try {
                    synchronized (mSurfaceHolder) {
                        mCanvas = mSurfaceHolder.lockCanvas();
                        //下面三句段代码  重置画板的作用 先清除xfermode对象再设置
                        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                        mCanvas.drawPaint(mPaint);
                        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                        for (int i = 0; i < mMeteorAddressModuleArray.size(); i++) {
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.meteor);
                            MeteorAddressModule meteorAddressModule = mMeteorAddressModuleArray.get(i);
                            meteorAddressModule.setmX(meteorAddressModule.getmX() - 5);
                            meteorAddressModule.setmY(meteorAddressModule.getmY() + 5);
                            mCanvas.drawBitmap(bitmap, meteorAddressModule.getmX(), meteorAddressModule.getmY(), mPaint);
                            if (meteorAddressModule.getmX() < 0 || meteorAddressModule.getmY() > MyApplication.SCREEN_HEIGHT) {
                                if (!bitmap.isRecycled()) {
                                    bitmap.recycle();
                                }
                                mMeteorAddressModuleArray.remove(meteorAddressModule);
                                initMeteorAddressMoudle();

                            }
                        }

                    }

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {
                    if (mCanvas != null) {
                        mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }

            }
        }
    }

    //绘画事件
    @Override
    public void surfaceCreated(SurfaceHolder holder) {//surface创建的时候
        DrawThread mDrawThread = new DrawThread(mSurfaceHolder);
        mDrawThread.start();


        isThreadOpen = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {//surfaceView改变的时候

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {//surfaceView销毁的时候
        isThreadOpen = false;
    }

}