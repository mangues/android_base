package com.mangues.lifecircleapp.data.database;

import com.mangues.lifecircleapp.LifeCircleApplication;
import com.mangues.lifecircleapp.data.database.bean.Test;
import com.mangues.lifecircleapp.data.database.gen.DaoMaster;
import com.mangues.lifecircleapp.data.database.gen.DaoSession;
import com.mangues.lifecircleapp.data.database.gen.TestDao;

import java.util.List;

/**
 * Created by mangues on 16/8/22.
 */

public class GreenDaoManager {
    private static final String GREEN_DB_NAME = "lifecircle-db";
    private static GreenDaoManager mInstance;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;


    public GreenDaoManager() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(LifeCircleApplication.getInstance(), GREEN_DB_NAME, null);
                DaoMaster mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
                mDaoSession = mDaoMaster.newSession();
            }
        }).start();


    }

    public static GreenDaoManager getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoManager();
                }
            }

        }
        return mInstance;
    }

    public DaoMaster getMaster() {
        return mDaoMaster;
    }

    public DaoSession getSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }



//    com.mangues.lifecircleapp.data.database.gen.TestDao userDao = GreenDaoManager.getInstance().getSession().getTestDao();
//    //        com.mangues.lifecircleapp.data.database.bean.Test user = new com.mangues.lifecircleapp.data.database.bean.Test(null, "manfues");
////        userDao.insert(user);
////        mNameET.setText("");
//    List<Test> findUser = userDao.queryBuilder().where(TestDao.Properties.Name.eq("manfues")).build().list();
//    MLogger.e(findUser.get(0).getName());
}
