package chire.util.download;

import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 多线程下载类
 */
public class ThreadDownload extends Downer{
    //线程下载数量，默认是1
    public static int threadCount=1;
    //记录各个子线程是否下载完毕，下载完一个减去1
    public static int runningThread=1;
    //文件总大小
    public volatile static int len=0;
    //文件进度
    public volatile static int progress;
    public ThreadDownload(String url_path, String save_path) {
        super(url_path, save_path);
    }

    public void setThreadCount(int threadCount){
        ThreadDownload.threadCount=threadCount;
        runningThread=threadCount;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(getUrl_path());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(100);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200) {
                //服务器返回的数据的长度
                int length = conn.getContentLength();
                ThreadDownload.len=length;
                System.out.println(length);
                //在客户端本地创建一个大小跟服务器创建一个大小和服务器相等的临时文件
                RandomAccessFile raf = new RandomAccessFile(getSave_path(), "rwd");
                //指定创建的这个文件的长度
                raf.setLength(length);
                raf.close();
                //计算平均每个线程下载的文件的大小
                int blockSize = length /threadCount;
                for(int i=1;i<=threadCount;i++){
                    //第一个线程下载的开始位置
                    int startIndex=(i-1)*blockSize;
                    //结束位置
                    int endIndex=i*blockSize-1;
                    //最后一个线程结束位置是文件末尾
                    if(i==threadCount){
                        endIndex=length;
                    }
                    System.out.println("线程："+i+"下载"+startIndex+"--->"+endIndex);
                    SonThreadDownload sonThreadDownload = new SonThreadDownload(getUrl_path(), getSave_path());
                    sonThreadDownload.setBpDownload(this.isBpDownload());
                    sonThreadDownload.setter(i,startIndex,endIndex);
                    //这里使用了线程池，可以new Thread(sonThreadDownload).start();
                    //MyExecutorService.getThread().submit(sonThreadDownload);
                    new Thread(sonThreadDownload).start();
                }
                //开始监听下载进度
                speed();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
    }

    private void speed() {
        int temp=0;
        //循环监控网速，如果下载进度达到100%就结束监控
        while(ThreadDownload.progress!=ThreadDownload.len) {
            //System.out.println("ThreadDownload.progress="+ThreadDownload.progress+"--ThreadDownload.len="+ThreadDownload.len);
            temp=progress;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //当前下载进度除以文件总长得到下载进度
            double p=(double)temp/(double)len*100;
            //当前下载进度减去前一秒的下载进度就得到一秒内的下载速度
            temp= progress-temp;

            //System.out.println(p);
            sl.speed(temp,p);
        }
        sl.speed(temp,100);
        System.out.println("整个文件下载完毕啦");
    }

    SpeedListener sl;
    /**
     *
     * @param sl 网速监听回调接口
     */
    public void addSpeedListener(SpeedListener sl){
        this.sl=sl;
    }

}

