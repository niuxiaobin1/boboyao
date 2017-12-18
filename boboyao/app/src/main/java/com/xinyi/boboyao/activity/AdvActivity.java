package com.xinyi.boboyao.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xinyi.boboyao.BaseActivity;
import com.xinyi.boboyao.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvActivity extends BaseActivity {

    private Timer timer;
    private int countdown = 4;

    @BindView(R.id.timer_tv)
    TextView timer_tv;
    @BindView(R.id.adv_image)
    ImageView adv_image;

    private int num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv);
        ButterKnife.bind(this);
        initTitle(R.string.zhuanmi_readAdv);

        startTimer();
    }


    //开始倒计时
    private void startTimer() {
        //点击获取验证码后改变按钮的状态,让按钮不可点击
        if (timer == null) {
            timer = new Timer(true);
        }
        countdown = 4;
        timer_tv.setEnabled(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 0, 1000);
    }


    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {


            if (countdown >= 0) {
                timer_tv.setText(countdown + "");
                timer_tv.setBackgroundResource(0);
                timer_tv.setEnabled(false);
            } else {
                timer_tv.setText("0");
                nextImage();

            }
//qq
            countdown--;

        }
    };

    public void onNext(View view){
            nextImage();
    }


    private void nextImage() {
        stopTimer();
        num++;
        if (num % 2 != 0) {

            adv_image.setImageResource(R.mipmap.advt);
        } else {
            adv_image.setImageResource(R.mipmap.advo);
        }
        startTimer();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
