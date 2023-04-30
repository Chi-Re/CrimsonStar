package chire.util.download;

import java.io.File;

/**
 * 线程下载抽象接口，增强了Runnable接口，主要增加下载断点开关
 */
public abstract class Downer implements Runnable {
    //开启是否断点下载，为了保护磁盘，默认关闭

    //文件下载地址
    private String url_path;
    //文件保存地址
    private String save_path;
    //下载进度保存位置，默认为C:\ProgramData
    private String temp_path="C:\\ProgramData\\";

    public String getTemp_path() {
        return temp_path;
    }

    public void setTemp_path(String temp_path) {
        this.temp_path = temp_path;
    }

    public Downer(String url_path, String save_path) {
        this.url_path = url_path;
        this.save_path = save_path;
    }

    public String getUrl_path() {
        return url_path;
    }

    public void setUrl_path(String url_path) {
        this.url_path = url_path;
    }
    public String getFileName(){
        return new File(save_path).getName();
    }
    public String getSave_path() {
        return save_path;
    }

    public void setSave_path(String save_path) {
        this.save_path = save_path;
    }

    public boolean bpDownload=false;

    public boolean isBpDownload() {
        return bpDownload;
    }

    public void setBpDownload(boolean bpDownload) {
        this.bpDownload = bpDownload;
    }
}

