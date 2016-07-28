/*
 * Copyright 2016. chenshufei
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

package com.mangues.lifecircleapp.injection.module;


import com.mangues.lifecircleapp.base.basemvp.BasePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/13
 * <br /> email: chenshufei2@sina.com
 */
@Module
public class PresenterModule {
    private BasePresenter mBasePresenter;
    public PresenterModule(BasePresenter basePresenter){
        this.mBasePresenter = basePresenter;
    }
    @Provides
    BasePresenter provideBasePresenter(){
        return mBasePresenter;
    }
}