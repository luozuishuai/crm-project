package com.shangma.cn.common.pool;

import com.shangma.cn.common.container.SpringBeanUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 单例模式获取多线程实例
 */
public class AsyncManager {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static AsyncManager asyncManager;

    private AsyncManager(){
        threadPoolTaskExecutor = SpringBeanUtils.getBean(ThreadPoolTaskExecutor.class);
    }

    public static AsyncManager getInstance(){
        if(asyncManager == null){
            synchronized (AsyncManager.class){
                if(asyncManager == null){
                    asyncManager = new AsyncManager();
                }
            }
        }
        return asyncManager;
    }

    public void execute(Runnable runnable){
        threadPoolTaskExecutor.execute(runnable);
    }

    public void shutdown(){
        threadPoolTaskExecutor.shutdown();
    }

}
