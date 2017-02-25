package com.yxb.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 用户获取 Lock 上的一个条件,也就是说 Condition 与 Lock 绑定的。Condition 用于进程间通信，
 * 它解决的wait(),notify(),notifyAll()难以使用的问题.Condition 为线程提供了一个含义，以便在某个状态
 * 条件可能为 true 的另一个线程通知它之前，一直挂起该线程。
 *
 * 在 finally 块里面调用 lock.unlock() 否者在出错的时候容易出现一直锁
 */
public class ReentrantLockAndCondition extends AppCompatActivity {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    Button notifyBtn;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reentrant_lock_and_condition);
        notifyBtn = (Button) findViewById(R.id.notify_btn);
        Log.d(getClass().getSimpleName(), "onCreate");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " start before lock");
                lock.lock();
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " start after lock");
                try {
                    condition.await();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReentrantLockAndCondition.this,"running again",Toast.LENGTH_SHORT).show();
                        }
                    });
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " continue");
                } catch (InterruptedException e) {
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " exception");
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " unlock");
                }
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " end");
            }
        });
        thread.start();
        notifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"click",Toast.LENGTH_SHORT).show();
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " start before lock");
                lock.lock();
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " start after lock");
                try {
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " start");
                    condition.signalAll();
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " continue");
                } finally {
                    lock.unlock();
                    Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " unlock");
                }
                Log.d(getClass().getSimpleName(), Thread.currentThread().getName() + " end");
            }
        });
    }

    private void example() {
        try {
            lock.lock();
        } finally {
            lock.unlock();
        }
    }
}
