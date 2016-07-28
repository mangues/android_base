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

import android.app.Application;
import android.content.Context;


import com.mangues.lifecircleapp.data.net.retrofitrxjava.NetworkDateSource;
import com.mangues.lifecircleapp.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/15
 * <br /> email: chenshufei2@sina.com
 */
@Module
public class ApplicationModule {

    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication(){
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    EventBus provideEventBus(){
        return EventBus.getDefault();
    }

    @Provides
    @Singleton
    NetworkDateSource provideNetworkDateSource(){
        return NetworkDateSource.getInstance();
    }

//    @Provides
//    @Singleton
//    AfeitaDb provideAfeitaDb(Application application){
//        return AfeitaDb.create(application, Constant.DB_NAME, true);
//    }

}
