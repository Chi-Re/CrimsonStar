package chire.ui;

import arc.Core;
import arc.math.Interp;
import arc.scene.actions.Actions;
import arc.scene.style.Drawable;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;

import static mindustry.Vars.state;
import static mindustry.Vars.ui;

/**使用臭猫的源代码，来自{@link mindustry.ui.fragments.HudFragment}的showToast*/
public class PopDialogs {
    private static long lastToast;

    public static void showToast(String text){
        showToast(Icon.ok, text);
    }

    public static void showToast(Drawable icon, String text){
        showToast(icon, -1, text);
    }

    public static void showToast(Drawable icon, float size, String text){
        if(state.isMenu()) return;

        scheduleToast(() -> {
            Sounds.message.play();

            Table table = new Table(Tex.button);
            table.update(() -> {
                if(state.isMenu() || !ui.hudfrag.shown){
                    table.remove();
                }
            });
            table.margin(12);
            var cell = table.image(icon).pad(3);
            if(size > 0) cell.size(size);
            table.add(text).wrap().width(280f).get().setAlignment(Align.center, Align.center);
            table.pack();

            //create container table which will align and move
            Table container = Core.scene.table();
            container.top().add(table);
            container.setTranslation(0, table.getPrefHeight());
            container.actions(Actions.translateBy(0, -table.getPrefHeight(), 1f, Interp.fade), Actions.delay(2.5f),
                    //nesting actions() calls is necessary so the right prefHeight() is used
                    Actions.run(() -> container.actions(Actions.translateBy(0, table.getPrefHeight(), 1f, Interp.fade), Actions.remove())));
        });
    }

    private static void scheduleToast(Runnable run){
        long duration = (int)(3.5 * 1000);
        long since = Time.timeSinceMillis(lastToast);
        if(since > duration){
            lastToast = Time.millis();
            run.run();
        }else{
            Time.runTask((duration - since) / 1000f * 60f, run);
            lastToast += duration;
        }
    }
}
