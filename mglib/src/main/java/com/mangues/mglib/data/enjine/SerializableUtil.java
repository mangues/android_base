package com.mangues.mglib.data.enjine;

import android.content.Context;
import android.os.Environment;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 */
public class SerializableUtil {

    private static String sdCardDir = "/CacheObjectData/";

    private static String getCacheObjectFileDir(Context mContext){
        String retVal = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            retVal = mContext.getExternalCacheDir()+sdCardDir;
        }else{
            retVal = mContext.getCacheDir()+sdCardDir;
        }
        return retVal;
    }

    /**
     * 将object对象 cache 缓存在文件中
     * @param fileName
     * @param object
     */
    public static final void saveObject(String fileName, Object object,Context mContext){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try{
            File dir = new File(getCacheObjectFileDir(mContext));
            if (!dir.exists()){
                boolean mkdirs = dir.mkdirs();
            }
            File f = new File(dir,fileName);
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(null != oos){
                    oos.close();
                }
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static final Object restoreObject(String fileName,Context mContext){
        File f = new File(getCacheObjectFileDir(mContext),fileName);
        if (!f.exists()){
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object retVal = null;
        try{
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            retVal = ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try{
                if (null != ois){
                    ois.close();
                }
                if (null != fis){
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }


}
