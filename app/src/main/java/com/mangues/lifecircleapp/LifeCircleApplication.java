package com.mangues.lifecircleapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.mangues.lifecircleapp.data.database.GreenDaoManager;
import com.mangues.lifecircleapp.injection.component.ApplicationComponent;
import com.mangues.lifecircleapp.injection.module.ApplicationModule;
import com.mangues.lifecircleapp.util.ImageOptHelper;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.Logger;
//import com.robin.lazy.logger.LazyLogger;
//import com.robin.lazy.logger.Log4JTool;
//import com.robin.lazy.logger.LogLevel;
//import com.robin.lazy.logger.PrinterType;
import com.mangues.lifecircleapp.injection.component.DaggerApplicationComponent;
//import org.apache.log4j.Level;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mangues on 16/7/12.
 */

public class LifeCircleApplication extends Application {
    private List<Activity> activitys = new LinkedList<Activity>();
    private static LifeCircleApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initImageLoader(getApplicationContext());
        initLogger();
        //初始化greenDao数据库
        GreenDaoManager.getInstance();
    }


    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            activitys.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if (activitys != null && activitys.size() > 0) {
            activitys.remove(activity);
        }
    }

    public void finishAll() {
        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }
    }





    private void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .defaultDisplayImageOptions(ImageOptHelper.getDefaultOptions())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /***
     * 初始化日志系统
     *
     * @throws
     * @see [类、类#方法、类#成员]
     */
    protected void initLogger() {
//        LazyLogger.init(/* PrinterType.FORMATTED */PrinterType.ORDINARY) // 打印类型
//                .methodCount(3) // default 2
//                .hideThreadInfo() // default shown
//                .logLevel(LogLevel.ALL) // default LogLevel.ALL(设置全局日志等级)
//                .methodOffset(2) // default 0
//                .logTool(/* new AndroidLogTool() *//*new SLF4JTool()*/new Log4JTool(Level.ERROR)); // Log4j中的Level与本框架的LogLevel是分开设置的(Level只用来设置log4j的日志等级)

//        Logger
//                .init(YOUR_TAG)                 // default PRETTYLOGGER or use just init()
//                .methodCount(3)                 // default 2
//                .hideThreadInfo()               // default shown
//                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
//                .methodOffset(2)                // default 0
//                .logAdapter(new AndroidLogAdapter()); //default AndroidLogAdapter
//    }

    }


    public static LifeCircleApplication getInstance() {
        return sInstance;
    }

    public static void setInstance(LifeCircleApplication sInstance) {
        LifeCircleApplication.sInstance = sInstance;
    }









    //dragger
    private ApplicationComponent mComponent;
    public ApplicationComponent getComponent(){
        if (null == mComponent){
            mComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mComponent;
    }


}
