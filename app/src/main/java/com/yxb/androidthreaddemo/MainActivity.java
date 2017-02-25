package com.yxb.androidthreaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button handlerBtn;
    Button looperBtn;
    Button waitBtn;
    Button callableBtn;
    Button reentrantLockAndConditionBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHandlerBtnFunction();
        setLooperFunction();
        setWaitBtnFunction();
        setCallableBtn();
        setReentrantLockAndConditionBtn();
    }

    /**
     * 设置到 Handler Message 页面按键
     */
    private void setHandlerBtnFunction(){
        handlerBtn = (Button) findViewById(R.id.start_activity_handler_btn);
        handlerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HandlerMessageLooperActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置到 Looper 页面按键
     */
    private void setLooperFunction(){
        looperBtn = (Button) findViewById(R.id.start_activity_looper_btn);
        looperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForkNewThreadsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置跳转到 线程控制的按键
     */
    private void setWaitBtnFunction(){
        waitBtn = (Button) findViewById(R.id.start_activity_wait);
        waitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WaitSleepJoinYieldActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置跳转到 Callable、 Future、FutureTask 页面的按键
     */
    private void setCallableBtn(){
        callableBtn = (Button) findViewById(R.id.start_activity_callable);
        callableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CallableFutureFutureTaskActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 设置跳转到 ReentrantLockAndCondition 页面的按键
     */
    private void setReentrantLockAndConditionBtn(){
        reentrantLockAndConditionBtn = (Button) findViewById(R.id.start_activity_reentrant);
        reentrantLockAndConditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReentrantLockAndCondition.class);
                startActivity(intent);
            }
        });
    }
}
