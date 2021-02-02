package demo;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadDemo {

    /**
     * 重写原生Thread线程
     */
    @Test
    public void test1(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("run咯");
            }
        }.start();
    }

    /**
     * 实现Runnabe接口
     */
    @Test
    public void test2(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("hha");
            }
        };
        new Thread(runnable).start();
    }

    /**
     * 实现Callable接口
     */
    @Test
    public void test3() throws Exception {
        Callable<String> callable = new Callable<String>(){
            @Override
            public String call() {
                System.out.println("被call了");
                return "呵呵呵";
            }
        };
        System.out.println("go");
        FutureTask<String> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();
        System.out.println("111");
        System.out.println(futureTask.get());
        System.out.println("222");

    }

    /**
     * Executors创建线程池(不带延迟功能)(JDK自带)
     */
    @Test
    public void test4(){
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello,world");
            }
        });
    }

    /**
     * Executors创建线程池（带延迟功能）(JDK自带)
     */
    @Test
    public void test5(){
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("hello");
            }
        },2,TimeUnit.SECONDS);
    }


    public static void main(String[] args) {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(10);
        for (int i = 0; i < 100; i++) {
            executor.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                    System.out.println("hello");
                }
            },2,TimeUnit.SECONDS);
        }
    }

}
