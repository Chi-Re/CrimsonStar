package chire;

import arc.Core;
import arc.Events;
import arc.scene.style.Drawable;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.Time;
import chire.content.CRBlocks;
import chire.content.CRPlanets;
import chire.content.CRTechTree;
import chire.content.CRUnitTypes;
import com.alibaba.fastjson2.JSONObject;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.mod.Mod;
import mindustry.ui.MobileButton;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static chire.util.CoreCache.*;
import static mindustry.Vars.state;
import static mindustry.Vars.ui;

public class ChireJavaMod extends Mod{
    private String searchtxt;
    private String keytxt;
    private int unitsCreated;
    private String downloadHttp;

    public ChireJavaMod(){
        Log.info("Loaded ExampleJavaMod constructor.");

        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("example-java-mod-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.cont.button(Icon.pencil, Styles.cleari, () -> {
                    BaseDialog dialog1 = new BaseDialog("@editmessage");
                    searchtxt = get(keytxt, String.class, String::new);
                    if (Objects.equals(searchtxt, "")) {
                        searchtxt = "null!!!";
//                        Jval.JsonArray data = new Jval.JsonArray();//commons-beanutils-1.8.0.jar、commons-collections、commons-lang-2.4、commons-logging-1.1、ezmorph-1.0.4、json-lib-2.3-jdk15
//                        data.add(Jval.valueOf("name"), Jval.valueOf(true));
//                        searchtxt = String.valueOf(data);
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("name", "chire");
//                        searchtxt = new JSONObject(map).toJSONString();
                        //searchtxt = jsonB(jsonA("alwaysUnlocked", true), jsonA("accessible", true), jsonA("visible", true));
                    }
                    dialog1.cont.field(keytxt, res -> {
                        keytxt = res;
                    }).size(1000f, 50f).row();
                    dialog1.cont.field(searchtxt, res -> {
                        searchtxt = res;
                    }).size(1000f, 50f);
                    dialog1.cont.button("添加", () -> {
                        put(keytxt, searchtxt);
                    }).size(100f, 50f);
                    dialog1.cont.button("清除", () -> {
                        remove(keytxt);
                    }).size(100f, 50f).row();

//                    dialog1.cont.button("下载", () -> {
//                        BaseDialog dialog3 = new BaseDialog("下载");
//                        dialog3.cont.field(downloadHttp, res -> {
//                            downloadHttp = res;
//                        }).size(1000f, 50f).row();
//                        dialog3.cont.button("下载", () -> {
////                            modImportProgress = 0f;
////                            ui.loadfrag.show("@downloading");
////                            ui.loadfrag.setProgress(() -> modImportProgress);
//
//                            //1、连接服务器，获取一个文件，获取文件的长度，在本地创建一个大小跟服务器文件一样大的临时文件
//                            ThreadDownload threadDownload = new ThreadDownload(downloadHttp, modDirectory.path());
//                            //设置线程数
//                            threadDownload.setThreadCount(1);
//                            //开启断点下载
//                            //threadDownload.setBpDownload(true);
//                            //添加进度和网速监听
////                            threadDownload.addSpeedListener(new SpeedListener() {
////                                @Override
////                                public void speed(int s, double progress) {
////                                    modImportProgress = (float) (progress/10);
////                                }
////
////                            });
//                            //由于ThreadDownload类也是个线程类，可以开启线程
//                            threadDownload.run();
//
//                        }).size(100f, 50f);
//                        dialog3.show();
//                    }).size(100f, 50f);

                    dialog1.cont.button("退出", dialog1::hide).size(100f, 50f);
                    dialog1.closeOnBack();
                    dialog1.show();
                }).size(100f, 50f);
                dialog.cont.button(Icon.admin, Styles.cleari, () -> {
                    ui.planet.state.planet = Planets.erekir;
                    BaseDialog dialog2 = new BaseDialog("@editmessage");
                    put("unitsCreated", unitsCreated);
                    dialog2.cont.add(getString("unitsCreated")).row();
                    dialog2.cont.button("ok", dialog2::hide).size(100f, 50f);
                    dialog2.show();
                }).size(100f, 50f);
                MobileButton set = new MobileButton(Icon.adminSmall, "打开弹窗", dialog::toggle);
                qwert(set);
                dialog.show();
            });
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example content.");
        CRBlocks.load();
        CRPlanets.load();
        CRTechTree.load();
        CRUnitTypes.load();
        runEvents();
    }
//老旧
    public void qwert(Button button) {
        Table container = Core.scene.table();
        float width = 230f;
        Drawable background = Styles.black6;
//        container.setSize(Core.graphics.getWidth(), Core.graphics.getHeight());
        //container.defaults().size(120f, 50f).pad(5).padTop(4f);
        container.marginLeft(-Core.graphics.getWidth());
        container.marginBottom(-Core.graphics.getHeight());
        container.add().width(Core.graphics.getWidth() / 10f);
        container.table(background, t -> {
            t.defaults().width(width).height(70f);
            t.add(button);
        }).width(width);
    }

    public void runEvents() {
        unitsCreated = getInt("unitsCreated");
        Events.on(EventType.UnitCreateEvent.class, e -> {
            if(e.unit.team == state.rules.defaultTeam){
                unitsCreated++;
            }
        });
    }

}
