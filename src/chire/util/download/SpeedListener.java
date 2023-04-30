package chire.util.download;

public interface SpeedListener {
    /**
     *
     * @param s 当前下载速度，单位字节
     * @param progress 下载进度，百分比
     */
    void speed(int s,double progress);
}


