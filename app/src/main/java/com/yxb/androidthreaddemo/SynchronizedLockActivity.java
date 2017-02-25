package com.yxb.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 同步锁
 */
public class SynchronizedLockActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronized_lock);
    }


    static class SynchronizedDemo{

        // 锁方法
        public synchronized void syncMethod(){

        }

        public void syncThis() throws InterruptedException {
            synchronized (this){
                wait(); // 会释放锁
            }
        }

        public void syncClassMethod(){
            synchronized (SynchronizedDemo.class){

            }
        }

        public synchronized static void syncStaticMethod(){

        }

    }
}
