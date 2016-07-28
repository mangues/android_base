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

package com.mangues.lifecircleapp.data.net.retrofitrxjava;


import android.content.Intent;

import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.base.BaseBean;
import com.mangues.lifecircleapp.base.CodeContant;
import com.mangues.lifecircleapp.ui.activity.LoginActivity;
import com.mangues.lifecircleapp.util.PubUtils;


import rx.functions.Action1;

/**
 * <br /> date: 16/1/6
 * <br /> email: chenshufei2@sina.com
 */
public abstract class SubscribeResult<T extends BaseBean> implements Action1<T>{
    @Override
    public void call(T t) {
        if (CodeContant.NOLOGIN.equals(t.getCode())){ //未登录
            Intent intent = new Intent(LifeCircleApplication.getInstance(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            LifeCircleApplication.getInstance().startActivity(intent);
        }else if (CodeContant.BUSINESSSUCCESS.equals(t.getCode())){ //业务处理成功
            onOk(t);
        }else{
            PubUtils.popTipOrWarn(LifeCircleApplication.getInstance(),t.getMessage()+"");
            onFailure(t);
        }
    }

    protected  void onFailure(T response){

    };

    protected abstract void onOk(T response);
}
