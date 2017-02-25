package com.yxb.androidthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Objects;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 使用读写分离思想解决部分同步问题
 * CopyOnWriteArrayList和CopyOnWriteArraySet内部实现了读写分离
 */
public class CopyOnWriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_on_write);
    }

    // 读写分离的集合，可以提高并发效率
    CopyOnWriteArrayList<Integer> arrayList = new CopyOnWriteArrayList<>();
    CopyOnWriteArraySet<Integer> arraySet = new CopyOnWriteArraySet<>();
    // 锁分段技术，提高并发效率
    ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();
    // 阻塞队列，很简单的实现了生产者-消费者模式
    BlockingDeque<Object> objectBlockingDeque = new LinkedBlockingDeque<>();

    private void add(int i){
        arrayList.add(i);
        arraySet.add(i);
    };

    private int get(int i){
        int j = arrayList.get(i);
        j = arrayList.get(i);
        return j;
    }

    private int remove(int i){
        arraySet.remove(i);
        return arrayList.remove(i);
    }

    private void set(int i,int value){
        if(arrayList.size() > i){
            arrayList.set(i,value);
        }
    }
}
