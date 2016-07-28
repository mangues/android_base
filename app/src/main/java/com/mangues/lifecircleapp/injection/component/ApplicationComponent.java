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

package com.mangues.lifecircleapp.injection.component;

import android.app.Application;
import android.content.Context;


import com.mangues.lifecircleapp.injection.ApplicationContext;
import com.mangues.lifecircleapp.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import de.greenrobot.event.EventBus;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/13
 * <br /> email: chenshufei2@sina.com
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {


    //void inject(CommonUtils commonUtils);

    @ApplicationContext
    Context context();

    Application application();

    EventBus eventBus();

    //NetworkDateSource networkDateSource();

//    AfeitaDb afeitaDb();
}
