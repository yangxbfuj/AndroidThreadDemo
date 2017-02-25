package com.yxb.androidthreaddemo;

import android.os.Handler;
import android.os.Looper;
import android.renderscript.RSRuntimeException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * 在UI线程中创建子线程 father ,在 father 线程中启动 Looper 并创建新的线程,
 * 在 onDestroy 中退出 father Looper 循环
 */

//TODO Looper.quit() 方法只是通知 Looper 不再接受新的线程，当前线程仍会被执行。
//TODO 那么如何抛弃队列中所有剩余的线程呢？
public class ForkNewThreadsActivity extends AppCompatActivity {

    private TextView threadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fork_new_threads);
        threadText = (TextView) findViewById(R.id.thread_run_info_text);
        threadText.setMovementMethod(new ScrollingMovementMethod());
        startAFatherThread();
    }

    Thread fatherThread;
    Handler fatherThreadHandler;
    Looper fatherThreadLooper;


    private void startAFatherThread(){
        fatherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                refreshUIText("father thread start running");
                Log.d("ForkNewThreadsActivity","fatherThread running start");
                Looper.prepare(); // 在Ui线程的自线程中创建新线程之前必须要调用
                fatherThreadHandler = new Handler();
                int i = 10;
                while(i-- > 0){
                    startACostTimeThread();
                    Log.d("ForkNewThreadsActivity","childThread start");
                }
                fatherThreadLooper = Looper.myLooper();
                refreshUIText("father thread's looper start loop");
                Log.d("ForkNewThreadsActivity","fatherThread start looping");
                Looper.loop(); //进入取消息队列的循环
                Log.d("ForkNewThreadsActivity","fatherThread running end");
                refreshUIText("father thread end running");
            }
        });
        fatherThread.start();
        Log.d("ForkNewThreadsActivity","fatherThread start");
    }

    private void startACostTimeThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                refreshUIText(Thread.currentThread().getName() + " start running");
                Log.d("This childThread id is ",Thread.currentThread().getName());
                fatherThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("This thread id is ",Thread.currentThread().getName());
                    }
                });
                refreshUIText(Thread.currentThread().getName() + " end running");
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        fatherThreadLooper.quit();// 结束father线程,让系统可以回收当前Activity
        super.onDestroy();
    }

    private void refreshUIText(String info){
        runOnUiThread(new RefreshUIRunnable(info,threadText));
    }

    static final class RefreshUIRunnable implements Runnable{

        String info;
        TextView textView;

        RefreshUIRunnable(String info,TextView textView){
            this.info = info;
            this.textView = textView;
        }

        @Override
        public void run() {
            textView.append(info + "\n");
        }
    }
}
