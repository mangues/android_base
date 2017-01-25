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

/**
 * 线程任务类
 * <br /> author: chenshufei
 * <br /> date: 15/10/9
 * <br /> email: chenshufei2@sina.com
 */
public abstract class ThreadTask implements Runnable{
    /**
     * 开始前执行的方法，子线程中
     */
    public void onBefore(){}

    @Override
    public void run() {
        onBefore();
        onRun();
        onAfter();
    }

    /**
     * 重写以执行任务
     */
    protected abstract void onRun();

    /**
     * execute后执行的方法，子线程中
     */
    public void onAfter(){}
}
