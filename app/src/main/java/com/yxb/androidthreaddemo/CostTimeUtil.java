package com.yxb.androidthreaddemo;

/**
 * Created by yangxb on 2017/2/23.
 */

public class CostTimeUtil {
    /**
     * 一个递归版本的斐波那契计算,表示一个耗时操作
     * @param n 第n
     * @return 返回第n个斐波那契数
     */
    public static int Fib(int n){
        if(n <= 2){
            return 1;
        }else {
            return Fib(n - 1) + Fib(n - 2);
        }
    }
}
