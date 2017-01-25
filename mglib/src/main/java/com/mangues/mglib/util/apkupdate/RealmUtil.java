package com.mangues.mglib.util.apkupdate;

import android.content.Context;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.List;


/**
 * Created by mangues on 17/1/24.
 */

public class RealmUtil {
    static LiteOrm liteOrm;
    public RealmUtil(Context context){
        if (liteOrm == null) {
            liteOrm = LiteOrm.newSingleInstance(context, "updateApk.db");
        }
        liteOrm.setDebugged(true); // open the log
    }

    public  static void save(DownloadRecord downloadRecord){

        liteOrm.save(downloadRecord);
    }

    public  static void update(DownloadRecord downloadRecord){

        liteOrm.save(downloadRecord);
    }

    public  void delete(Integer recordId, Integer version){
        liteOrm.delete(new WhereBuilder(DownloadRecord.class)
                .where(" recordId=? and version=?", new String[]{"%"+recordId+"%","%"+version+"%"}));
    }


    public  List<DownloadRecord> find(Integer recordId, Integer version){
        List<DownloadRecord> list = liteOrm.query(new QueryBuilder<DownloadRecord>(DownloadRecord.class)
                .where(" recordId=? and version=?", new String[]{recordId+"",version+""}));


        return list;
    }



}
