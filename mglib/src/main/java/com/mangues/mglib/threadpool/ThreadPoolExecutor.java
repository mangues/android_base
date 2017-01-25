/*
 * Copyright 2015. chenshufei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mangues.mglib.threadpool;


import com.mangues.mglib.bean.DeviceInfo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

/**
 * 线程池管理者,内部自动维持一个线程队列。
 * 若新增过来的execute超过当前的numberOfCPUCores*5+2正在执行的线程数，则放入到线程队列中等待。
 * 初始化线程池中维持numberOfCPUCores*5+2个线程待执行。
 * <br /> author: chenshufei
 * <br /> date: 15/10/9
 * <br /> email: chenshufei2@sina.com
 */
public class ThreadPoolExecutor {
    private static ThreadPoolExecutor threadPoolExecutor;

    private ExecutorService threadExecutor;

    private ThreadPoolExecutor(){
        //获取CUP个数
        int numberOfCPUCores = DeviceInfo.getNumberOfCPUCores();
        if (numberOfCPUCores <= 1){
            numberOfCPUCores = 1;
        }


        threadExecutor = Executors.newFixedThreadPool(numberOfCPUCores*5+2);
    }

    public static ThreadPoolExecutor getInstance() {
        if (null == threadPoolExecutor){
            synchronized (ThreadPoolExecutor.class){
                if (null == threadPoolExecutor){
                    threadPoolExecutor = new ThreadPoolExecutor();
                }
            }
        }
        return threadPoolExecutor;
    }

    /**
     * 将线程任务加入到线程池执行中，若当前任务未超过numberOfCPUCores*5+2最大值时，立即执行。
     * 若超过，则排队等待执行。
     * @param task
     */
    public void execute(ThreadTask task){
        threadExecutor.execute(task);
    }

    /**
     * 关闭停止 池程线的执行。
     */
    public void shutdown(){
        threadExecutor.shutdown();
    }


    //--------------------------------------以下部分直接copy jdk ExecutorService 接口 并加入实现 --------------

    /**
     * Submits a value-returning task for execution and returns a
     * Future representing the pending results of the task. The
     * Future's {@code get} method will return the task's result upon
     * successful completion.
     *
     * <p>
     * If you would like to immediately block waiting
     * for a task, you can use constructions of the form
     * {@code result = exec.submit(aCallable).get();}
     *
     * <p>Note: The {@link Executors} class includes a set of methods
     * that can convert some other common closure-like objects,
     * for example, {@link java.security.PrivilegedAction} to
     * {@link Callable} form so they can be submitted.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    public <T> Future<T> submit(Callable<T> task){
        return threadExecutor.submit(task);
    }

    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return the given result upon successful completion.
     *
     * @param task the task to submit
     * @param result the result to return
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    public <T> Future<T> submit(Runnable task, T result){
        return threadExecutor.submit(task,result);
    }


    /**
     * Submits a Runnable task for execution and returns a Future
     * representing that task. The Future's {@code get} method will
     * return {@code null} upon <em>successful</em> completion.
     *
     * @param task the task to submit
     * @return a Future representing pending completion of the task
     * @throws RejectedExecutionException if the task cannot be
     *         scheduled for execution
     * @throws NullPointerException if the task is null
     */
    public Future<?> submit(Runnable task){
        return threadExecutor.submit(task);
    }



}
