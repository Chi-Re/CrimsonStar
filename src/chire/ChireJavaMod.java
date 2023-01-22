package chire;

import arc.*;
import arc.scene.ui.layout.Table;
import arc.util.*;
import chire.content.*;
import chire.util.CRUtil;
import mindustry.Vars;
import mindustry.game.EventType.*;
import mindustry.gen.Icon;
import mindustry.mod.*;
import mindustry.ui.MobileButton;
import mindustry.ui.dialogs.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

//这里我测试中文是否可以正常支持,InitiateUI
//Provides integration with GitHub.
//Specify your GitHub account to access all of your repositories from the IDE.
//Clone repositories, create and review pull requests, create gists, and much more.
//To configure, open Settings / Preferences and go to Version Control | GitHub.
//Requires the Git plugin. coiled
public class ChireJavaMod extends Mod{
    private final Table table = new Table();
    private final float num = 6, size = 45 * num;
    private final double lastX = 0, lastY = 0;
    float width = Vars.android ? 250 : 300;
    float height = 60;
    private static InitiateUI.CRUI.Function function = new InitiateUI.CRUI.Function();

    public ChireJavaMod(){
        Log.info("Loaded ChireJavaMod constructor.");

        //listen for game load event,弹窗
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("绯红星海");
                //dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Core.atlas.find("crimson-star-icon")).size(250f, 250f).pad(20f).row();
                dialog.cont.add("我的一个java模组,现在正在在玩法上的制作,可能会用很长很长时间\n大部分都还在测试,剧情已经写好了\n我的模组群:587237182\n弹窗可以在设置里关闭\n\n嗯,没什么可说的了\n偷偷的说:我才不会更新UI界面,这个很麻烦而且没有用").row();
                dialog.cont.button("更新日志", () -> {
                    var dialog2 = new BaseDialog("更新日志");
                    dialog2.cont.add("0.0.2\n模组改名为绯红星海(炽工业)\n\n0.0.1\n没更新呢,剧情已经写好了\n测试测试").row();
                    dialog2.buttons.defaults().size(210, 64);
                    dialog2.addCloseButton();
                    dialog2.show();
                }).size(100f, 50f);
                //dialog.hide();
                //dialog.cont.button("确定", dialog::hide).size(100f, 50f);

                //BaseDialog dialog1 = new BaseDialog("炽工业");

                dialog.hidden(() -> {
//                    dialog1.cont.pane(inner -> inner.pane(table -> table.pane(t1 -> {
//                        t1.table(Tex.buttonSideLeftDown, (ta1 -> {
//                            ta1.add("嗯,没什么可说的了").row();
//                            function.showToastDialog(dialog1, ta1, 1.2F, 0.9F);
//                        }));
//                    })));
                    function.showToast(Icon.adminSmall, 32, "喵?");
                });

                MobileButton set = new MobileButton(Icon.adminSmall, "打开弹窗", dialog::toggle);
                function.qwert(set);
                //MenuFragment menufrag = new MenuFragment();
                //MenuFragment.addButton("!!!!", Icon.exit, () -> Core.app.exit());

                Vars.ui.settings.game.checkPref("绯红星海弹窗", Core.settings.getBool("炽工业弹窗"));
                Vars.ui.hudGroup.fill(t -> {
                    t.visibility = (() -> Core.settings.getBool("绯红星海弹窗"));
                    t.top().left().add(table);
                    //t.addListener(new InputListener());
			    });
                if (Core.settings.getBool("绯红星海弹窗")){
                    dialog.show();
                }
                dialog.addCloseButton();
            });
                //MenuFragment wes = new MenuFragment();
                //wes.addButton("whit", Icon.exit, () -> Core.app.exit());
                //wes.addButton(new MenuFragment.MenuButton("whit", Icon.exit, ui.mods::show));
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading some example example.content.");
        CRItems.load();
        CRBlocks.loadEnv();
        CRPlanets.load();
        CRTechTree.load();
        CRUnitTypes.load();
        CRWeathers.load();
//        CRUtil.main();
    }
}
