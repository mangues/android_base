package com.mangues.lifecircleapp.data.database.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.mangues.lifecircleapp.data.database.bean.Test;

import com.mangues.lifecircleapp.data.database.gen.TestDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig testDaoConfig;

    private final TestDao testDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        testDaoConfig = daoConfigMap.get(TestDao.class).clone();
        testDaoConfig.initIdentityScope(type);

        testDao = new TestDao(testDaoConfig, this);

        registerDao(Test.class, testDao);
    }
    
    public void clear() {
        testDaoConfig.clearIdentityScope();
    }

    public TestDao getTestDao() {
        return testDao;
    }

}
