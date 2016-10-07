package com.justwayward.reader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.justwayward.reader.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.tvSkip)
    TextView tvSkip;

    private boolean flag = false;
    private Runnable runnable;
    private static int CURRENT_COUNT = 3;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0){
                if(CURRENT_COUNT > 0) {
                    tvSkip.setText(--CURRENT_COUNT + " s");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                goHome();
            }
        };

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
        tvSkip.postDelayed(runnable, CURRENT_COUNT*1000);
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (!flag){
                    try {
                        Thread.sleep(1000);
                        mHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private synchronized void goHome() {
        if (!flag) {
            flag = true;
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        flag = true;
        tvSkip.removeCallbacks(runnable);
        ButterKnife.unbind(this);
    }
}
