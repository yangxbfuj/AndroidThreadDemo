package com.yxb.androidthreaddemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 本 Activity 主要用于展示 Callable、Future、FutureTask 的用法。
 *
 * 与 Runnable 的不同之处 ：Runnable 既能用于线程池、也能用于线程池，而这3个伙计只能用于线程池
 * 1.Callable 是一个范型接口,可以返回一个响应的返回值
 * 2.Future 对线程池制定了一个可以管理的任务标准。它提供了对 Runnable 或者 Callable 任务的
 *  执行结果进行取消、查询是否完成、获取结果、设置结果操作，分别对应 cancel,isDone,get,set函数。
 *  get方法会阻塞当前线程，知道任务返回结果。
 * 3.Future 只是定义了一些规范的接口，而 FutureTask 是其实现类。FutureTask 实现了 RunnableFuture<V>,
 *  而 RunnableFuture 实现了 Runnable 又实现了 Future<V> 这两个接口。
 */
public class CallableFutureFutureTaskActivity extends AppCompatActivity {

    static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callable_future_future_task);
        try {
            futureWithRunnable();
            futureWithCallBack();
            futureTask();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void futureWithRunnable() throws ExecutionException, InterruptedException {
        Future<?> futureTask = mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                CostTimeUtil.Fib(20);
            }
        });
        // get() 会阻塞当前线程
        Log.d("futureWithRunnable " ,"" + futureTask.get());
    }

    private void futureWithCallBack() throws ExecutionException, InterruptedException {
        Future<Integer> integerFutureTask = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return CostTimeUtil.Fib(20);
            }
        });
        // get() 会阻塞当前线程
        Log.d("futureWithCallBack " ,"" + integerFutureTask.get());
    }

    private void futureTask() throws ExecutionException, InterruptedException {
        FutureTask<Integer> integerFuture = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return CostTimeUtil.Fib(20);
            }
        });
        mExecutor.submit(integerFuture);
        // get() 会阻塞当前线程
        Log.d("futureWithCallBack " ,"" + integerFuture.get());
    }

    /**
     * 示例 不能调用
     */
    private void exampleForExecutor(){
        // 关闭线程池
        mExecutor.shutdown();
        mExecutor.shutdownNow();
        // 提交一个 Callable
        mExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        });
        // 提交一个 Runnable
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        // 提交一个 Runnable 和 返回值，内部将其转换为了一个 Callable
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {

            }
        },"");
        // 提交一个 FutureTask
        mExecutor.submit(new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        }));
    }
}
