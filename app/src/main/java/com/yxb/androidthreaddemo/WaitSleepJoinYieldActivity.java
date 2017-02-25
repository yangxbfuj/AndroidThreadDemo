package com.yxb.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 线程的wait、sleep、join 和 yield
 * 1.wait(): 当一个线程执行到 wait() 时,它进入到一个和该对象相关的等待池中，同时释放了对象的锁，
 * 使其它对象可以访问。使用 notify 、notifyAll或者制定睡眠时间来唤醒当前等待池中的线程。
 * 2.sleep() : Thread 的静态函数，作用是使调用线程进入睡眠状态。因为 sleep() 是 Thread 的
 * static 方法，因此它不能改变对象的锁。所以当在一个 Synchronized 块中调用sleep方法时，线程
 * 虽然休眠了，但是对象锁并没有释放，其它线程无法访问该对象。
 * 3.join : 等待目标线程执行完成后在继续执行
 * 4.yield: 线程礼让。目标线程由运行状态转换为就绪状态，也就是出让执行权限，让其它线程得以优先执行，
 * 但其它线程能否执行未知。
 */
public class WaitSleepJoinYieldActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_sleep_join_yield);
    }

    /**
     * 代码实例方法，千万不要调用
     * @throws InterruptedException
     */
    private void forExample() throws InterruptedException {
        Thread currentThread = Thread.currentThread();
        currentThread.wait();           // 等待，释放锁
        currentThread.wait(1000);       // 等待1000毫秒，释放锁
        currentThread.wait(1000,1000);  // 第二个参数为纳秒，总时间为毫秒+纳秒
        currentThread.notify();         // 唤起线程
        currentThread.notifyAll();      // 唤起线程

        currentThread.sleep(1000);      // 睡眠1000毫秒
        currentThread.sleep(1000,1000); // 睡眠，总时间为毫秒+纳秒

        Thread newThread = new Thread();
        newThread.join();               // 当前线程将等待 newThread 执行完毕后执行下一条语句
        newThread.join(1000);           // 最多等待 newThread 1000毫秒
        newThread.join(1000,1000);      // 最多等待 newThread 毫秒+纳秒

        currentThread.yield();          // 主动进入就绪状态，让出执行权限

    }
}
