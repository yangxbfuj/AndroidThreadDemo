package com.yxb.androidthreaddemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 此Activity为使用Handler的demo
 */
public class HandlerMessageLooperActivity extends AppCompatActivity {

    TextView threadInfoText;
    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hander_message_looper);
        initThreadInfoText();
        initStartBtn();
    }

    private void initThreadInfoText(){
        threadInfoText = (TextView) findViewById(R.id.thread_run_info_text);
        threadInfoText.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initStartBtn(){
        startBtn = (Button) findViewById(R.id.threads_start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int loop = 100;
                while(loop > 0 ) {
                    startAThreadByMyHandler(loop -- );
                }
            }
        });
    }

    /**
     * 使用 runOnUiThread 方法更新UI
     * @param i 自定义新线程的 id
     */
    private void startAThread(final int i) {
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CostTimeUtil.Fib(35);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        threadInfoText.append(String.format("Finish Thread %d\n", i));
                    }
                });
            }
        });
        newThread.start();
    }

    /**
     * 使用 使用handler 传递消息更新UI
     * 1.可以使用 Message.sendToTarget()
     * 2.可以使用 Handler.post(Runnable)或者Handler.sendMessage() 如注释
     * @param i
     */
    private void startAThreadByMyHandler(final int i){
        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {
                CostTimeUtil.Fib(35);
                Message.obtain(myHandler,new Runnable() {
                    @Override
                    public void run() {
                        threadInfoText.append(String.format("Finish Thread %d\n", i));
                    }
                }).sendToTarget();

//                myHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        threadInfoText.append(String.format("Finish Thread %d\n", i));
//                    }
//                });
            }
        });
        newThread.start();
    }

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            msg.getCallback().run();
        }
    };

}
