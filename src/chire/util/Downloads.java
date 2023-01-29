package chire.util;

import arc.files.Fi;
import arc.math.geom.Vec2;
import arc.scene.ui.Button;
import arc.util.Log;
import arc.util.Time;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.ui.dialogs.BaseDialog;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static mindustry.Vars.modDirectory;
import static mindustry.Vars.ui;

public class Downloads {
    public Fi jzfile;
    public Fi updatalistfile;
    public String jzfilepath;
    public boolean loaded;
    public String name;
    public List<UpdataData> updatadata = new ArrayList<>();

    public boolean load(BaseDialog dialog){
        dialog.cont.button("更新测试", () -> {
            downloadsfile("https://ghproxy.com/https://raw.githubusercontent.com/Chi-Re/CrimsonStar/master/updata.json", updatalistfile.path(), () -> {
                //https://github.com/Chi-Re/CrimsonStar/blob/c6e50afaa962b9f0470daa344ca86f9d27bcff3a/updata.json
                initial();
                var dialog3 = new BaseDialog("更新测试");
                dialog3.cont.add(updatadata.get(0).content).row();
                dialog3.buttons.defaults().size(210, 64);
                dialog3.addCloseButton();
                dialog3.show();
            });
        }).size(100f, 50f);
        return false;
    }
    public void disableSslVerification() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            Log.err(e);
            ui.showInfo("SSL认证失败");
        }
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean downloadsfile(String url, String filepath, float delay, boolean notOkRun, Runnable runnable) {
        AtomicBoolean yes = new AtomicBoolean(true);
        new Thread(() -> {
            disableSslVerification();
            File file = new File(filepath);
            if (file.exists()) {
                file.delete();
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.err(e.getMessage());
                }
            }
            try {
                URL urll = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(8 * 1000);
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(filepath);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    yes.set(true);
                    fos.close();
                    is.close();
                    if (runnable == null) return;
                    Time.runTask(delay, runnable);
                }
            } catch (Exception e) {
                Log.err("下载文件失败" + e.getMessage());
                ui.showErrorMessage("下载文件失败" + e.getMessage());
                if (runnable == null || !notOkRun) return;
                Time.runTask(delay, runnable);
            }
        }).start();
        return yes.get();
    }
    public boolean downloadsfile(String url, String filepath, Runnable runnable) {
        return downloadsfile(url, filepath, 20, true, runnable);
    }
    public void settingFile() throws IOException {
        Fi jz = new Fi(modDirectory + "/jz.json");
        Fi updatalist = new Fi(modDirectory + "/updatalist.json");
        Fi fileParent = jz.parent();
        if (!fileParent.exists()) {
            fileParent.mkdirs();
        }
        if (!jz.exists()) loaded = jz.file().createNewFile();
        jzfilepath = jz.path();
        jzfile = jz;
        if (!updatalist.exists()) loaded = updatalist.file().createNewFile();
        updatalistfile = updatalist;
    }
    public void initial() {
        Fi updatalist = new Fi(Vars.modDirectory + "/updatalist.json");
        Jval val = Jval.read(updatalist.readString());
        if (val.isArray()) {
            Jval.JsonArray jsonArray = val.asArray();
            for (Jval jval : jsonArray) {
                updatadata.add(new UpdataData(jval.getString("version"), jval.getString("url"), jval.getString("description")));
            }
        }
    }
    static class UpdataData {
        public String version;
        public String url;
        public String content;

        public UpdataData(String version, String url, String content) {
            this.version = version;
            this.url = url;
            this.content = content;
        }
    }
}
