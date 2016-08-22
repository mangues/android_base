package com.mangues.lifecircleapp.data.database.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by mangues on 16/8/22.
 */
@Entity
public class Test {
    @Id
    private Long id;
    private String name;
    @Transient
    private int tempUsageCount; // not persisted
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 294966613)
    public Test(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 372557997)
    public Test() {
    }
}
