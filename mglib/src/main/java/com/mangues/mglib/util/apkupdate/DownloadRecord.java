package com.mangues.mglib.util.apkupdate;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;


/**
 * <br /> author: chenshufei
 * <br /> date: 16/7/29
 * <br /> email: chenshufei2@sina.com
 */
@Table("downloadRecord_model")
public class DownloadRecord   implements Serializable{
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    private int recordId; //业务记录id
    private String urlAddress;
    private String fileName;
    private int version;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     *UNDOWNLOAD = 1;  //未下载 ，包括第一次刚点击视频下载时与退出后正在视频的文件时
     DOWNLOADING = 2; //下载中.... 继续
     PAUSE = 3;  //暂停中....
     FINISH = 4; //完成状态
     */



    private int downloadStatus; //下载状态

    private long totalLen = 0; //文件下载的总长度

    private long finishLen = 0; //已经下载的长度

    private long createTime; //下载创建时间

    private String filePath; //文件下载存放的地址

    public String getUrlAddress() {
        return urlAddress;
    }

    public void setUrlAddress(String urlAddress) {
        this.urlAddress = urlAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }

    public long getTotalLen() {
        return totalLen;
    }

    public void setTotalLen(long totalLen) {
        this.totalLen = totalLen;
    }

    public long getFinishLen() {
        return finishLen;
    }

    public void setFinishLen(long finishLen) {
        this.finishLen = finishLen;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

}