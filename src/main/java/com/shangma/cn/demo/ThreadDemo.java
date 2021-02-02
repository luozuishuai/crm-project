package com.shangma.cn.demo;

public class ThreadDemo {

    public static void main(String[] args) {
        //方式一：
        new Thread(){
            @Override
            public void run() {
                System.out.println("run咯");
            }
        }.start();
    }
}
