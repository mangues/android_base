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


import com.mangues.lifecircleapp.injection.PerActivity;
import com.mangues.lifecircleapp.injection.module.PresenterModule;

import dagger.Component;

/**
 * <br /> author: chenshufei
 * <br /> date: 16/1/13
 * <br /> email: chenshufei2@sina.com
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = PresenterModule.class)
public interface PresenterComponent {
    //BasePresenter,这样的泛型不支持，，，不知道什么情况，加了这个就生成不了component了...
    //void inject(BasePresenter basePresenter);

    //void inject(MainPresenter mainPresenter);
}
