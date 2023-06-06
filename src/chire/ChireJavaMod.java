package chire;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.func.Boolp;
import arc.func.Cons;
import arc.func.Floatc;
import arc.func.Intc;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.Drawable;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.util.Log;
import arc.util.OS;
import arc.util.Time;
import chire.content.*;
import chire.ui.CRJoinDialog;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.core.Version;
import mindustry.game.EventType;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.mod.Mod;
import mindustry.ui.Bar;
import mindustry.ui.MobileButton;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.SettingsMenuDialog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import static chire.util.CoreCache.*;
import static mindustry.Vars.*;

public class ChireJavaMod extends Mod{
    private String searchtxt;
    private String keytxt;
    private int unitsCreated;
    private static final String ModName = "crimson-star";

    public ChireJavaMod(){
        Log.info(logInfo("#CHIRE# 模组加载"));

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
                        searchtxt = Core.settings.getString("ip");
//                        Jval.JsonArray data = new Jval.JsonArray();//commons-beanutils-1.8.0.jar、commons-collections、commons-lang-2.4、commons-logging-1.1、ezmorph-1.0.4、json-lib-2.3-jdk15
//                        data.add(Jval.valueOf("name"), Jval.valueOf(true));
//                        searchtxt = String.valueOf(data);
//                        Map<String, Object> map = new HashMap<>();
//                        map.put("name", "chire");
                        //TODO 输出测试区
//                        Map<String, Object> map = jsonMap();
//                        jsonPut(map, "name", "chire");
//                        JSONObject jsonObject = jsonObject(map);
//                        searchtxt = jsonObject.toString();
//                        searchtxt = getString("data");
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
                    dialog1.cont.button("下载", () -> {
                        boolean[] cancel = {false};
                        float[] progress = {0};
                        int[] length = {0};
                        Fi file = bebuildDirectory.child("asa.mp4");
                        String updateUrl = "http://192.168.0.105:5244/d/%E6%88%91%E7%9A%84%E7%A1%AC%E7%9B%98/%E8%A7%86%E9%A2%91/%E5%85%B6%E4%BB%96/A%E7%BA%A7%E9%95%BF.mp4";

                        BaseDialog dialogdown = new BaseDialog("@be.updating");
                        download(updateUrl, file, i -> length[0] = i, v -> progress[0] = v, () -> cancel[0], () -> {

                        }, ed -> {
                            dialogdown.hide();
                            ui.showException(ed);
                        });

                        dialogdown.cont.add(new Bar(() -> length[0] == 0 ? Core.bundle.get("be.updating") : (int)(progress[0] * length[0]) / 1024/ 1024 + "/" + length[0]/1024/1024 + " MB", () -> Pal.accent, () -> progress[0])).width(400f).height(70f);
                        dialogdown.buttons.button("@cancel", Icon.cancel, () -> {
                            cancel[0] = true;
                            dialogdown.hide();
                        }).size(210f, 64f);
                        dialogdown.setFillParent(false);
                        dialogdown.show();
                    }).size(100f, 50f).row();

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
    public void init() {
        Log.info(logInfo("加载: 模组内容初始化(init)"));
        //修改原版
        //检查是否为第一次启动(true时代表不是第一次启动)
        put("started", true);
        CROverride.overrideBlock();
        CROverride.overrideUnit();
        CROverride.overrideUI();
        if(Vars.ui != null && Vars.ui.settings != null) {
            Vars.ui.settings.addCategory(getText("crimson-star.setting"), getName("icon"), settingsTable -> {
                settingsTable.pref(new SettingsMenuDialog.SettingsTable.Setting(Core.bundle.get("eu-show-me-now")) {
                    @Override
                    public void add(SettingsMenuDialog.SettingsTable table) {
                        table.row();
                    }
                });
            });
        }
    }

    @Override
    public void loadContent(){
        Log.info(logInfo("加载: 模组内容(loadContent)"));
        CRBlocks.load();
        CRPlanets.load();
        CRPlanets.init();
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

    public static String getText(String str){
        return Core.bundle.format(str);
    }
    public static String getName(String add){
        return ModName + "-" + add;
    }
    public static  String logInfo(String info){
        return "[" + ModName + "] " + info;
    }
    public static TextureRegion Load(String name){
        return Core.atlas.find(name);
    }

    private void download(String furl, Fi dest, Intc length, Floatc progressor, Boolp canceled, Runnable done, Cons<Throwable> error) {
        mainExecutor.submit(() -> {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(furl).openConnection();
                BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                OutputStream out = dest.write(false, 4096);

                byte[] data = new byte[4096];
                long size = con.getContentLength();
                long counter = 0;
                length.get((int) size);
                int x;
                while ((x = in.read(data, 0, data.length)) >= 0 && !canceled.get()) {
                    counter += x;
                    progressor.get((float) counter / (float) size);
                    out.write(data, 0, x);
                }
                out.close();
                in.close();
                if (!canceled.get()) done.run();
            } catch (Throwable e) {
                error.get(e);
            }
        });
    }
}
