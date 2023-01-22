package InitiateUI.CRUI;

import arc.Core;
import arc.Events;
import arc.files.Fi;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.geom.Vec2;
import arc.scene.actions.Actions;
import arc.scene.style.Drawable;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Cell;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.MobileButton;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;

public class Function {
    private long lastToast;
    private static Table submenu;
    private static Button currentMenu;

    public void showToastDialog(BaseDialog dialog, Table back, float startSpeed, float backSpeed) {
        showToastDialog(dialog, back, startSpeed, backSpeed, false);
    }

    public void showToastDialog(BaseDialog dialog, Table back, float startSpeed, float backSpeed, boolean StopMusic) {
        scheduleToast(() -> {
            Table container = Core.scene.table();
            dialog.cont.margin(6);
            dialog.cont.pack();
            container.top().add(dialog.cont);
            container.setTranslation(0, dialog.getPrefHeight() * 0.92F);
            container.actions(Actions.translateBy(0, -dialog.getPrefHeight() - 5, startSpeed, Interp.fastSlow));
            back.button("@back", Icon.left, () -> {
                container.actions(Actions.translateBy(0, dialog.cont.getPrefHeight() * 1.35F, backSpeed, Interp.fastSlow), Actions.remove());
            }).height(70).growX().row();
        });
    }

    public void scheduleToast(Runnable run) {
        long duration = (int) (1.5 * 1000);
        long since = Time.timeSinceMillis(lastToast);
        if (since > duration) {
            lastToast = Time.millis();
            run.run();
        } else {
            Time.runTask((duration - since) / 1000f * 60f, run);
            lastToast += duration;
        }
    }

    public void showToast(Drawable icon, float size, String text) {
        showToast(icon, size, text, null);
    }


    public void showToast(Drawable icon, float size, String text, Color color) {
        scheduleToast(() -> {
            Sounds.message.play();
            Table table = new Table(Tex.buttonSideLeft);
            table.setColor(Pal.lancerLaser);
            table.margin(6);
            Cell<Image> cell = table.image(icon).pad(3);
            if (size > 0) cell.size(size);
            table.add(text).color(color == null ? Color.white : color).wrap().width(280f).get().setAlignment(Align.center, Align.center);
            table.image(Core.atlas.find("crimson-star-icon")).size(64).row();
            table.pack();
            Table container = Core.scene.table();
            container.top().add(table);
            container.setTranslation(0, table.getPrefHeight());
            container.actions(Actions.translateBy(0, -table.getPrefHeight() - 30, 1.72F, Interp.pow2Out), Actions.delay(0.55F),
                    Actions.run(() -> container.actions(Actions.translateBy(0, 500, 1.7F, Interp.pow2Out), Actions.remove())));
        });
    }

    private static void checkPlay(Runnable run) {

        if (!mods.hasContentErrors()) {
            run.run();
        } else {
            ui.showInfo("@mod.noerrorplay");
        }
    }

    //(测试)放置在桌面的按钮?
    public void testcr(Button asdtest) {
        Table container = Core.scene.table();
        container.clear();
        container.name = "buttons";
        container.setSize(Core.graphics.getWidth(), Core.graphics.getHeight());

        float size = 120f;
        container.defaults().size(size).pad(5).padTop(4f);
        //container.top().add(asdtest);
        container.marginTop(60f);

        MobileButton play = new MobileButton(Icon.play, "@campaign", () -> checkPlay(ui.planet::show)), custom = new MobileButton(Icon.rightOpenOut, "@customgame", () -> checkPlay(ui.custom::show)), maps = new MobileButton(Icon.download, "@loadgame", () -> checkPlay(ui.load::show)), join = new MobileButton(Icon.add, "@joingame", () -> {
            checkPlay(ui.join::show);
        }), editor = new MobileButton(Icon.terrain, "@editor", () -> checkPlay(ui.maps::show)), tools = new MobileButton(Icon.settings, "@settings", ui.settings::show), mods = new MobileButton(Icon.book, "@mods", ui.mods::show), exit = new MobileButton(Icon.exit, "@quit", () -> Core.app.exit());

        if (!Core.graphics.isPortrait()) {
            container.add(play);
            container.add(join);
            container.add(custom);
            container.add(maps);
            container.row();
            container.table(table -> {
                table.defaults().set(container.defaults());
                table.add(editor);
                table.add(tools);
                table.add(mods);
                if (!ios) {
                    table.add(exit);
                }
                table.add(asdtest);
            }).colspan(4);
        } else {
            container.add(play);
            container.add(maps);
            container.row();
            container.add(custom);
            container.add(join);
            container.row();
            container.add(editor);
            container.add(tools);
            container.row();
            container.table(table -> {
                container.marginTop(0f);
                table.defaults().set(container.defaults());
                table.add(mods);
                if (!ios) {
                    table.add(exit);
                }
                table.add(asdtest);
            }).colspan(2);
        }
    }

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


    private static void buttons(Table t, Buttoni... buttons) {
        for (Buttoni b : buttons) {
            if (b == null) {
                continue;
            }
            Button[] out = {null};
            out[0] = t.button(b.text, b.icon, Styles.flatToggleMenut, () -> {
                if (currentMenu == out[0]) {
                    currentMenu = null;
                    fadeOutMenu();
                } else {
                    if (b.submenu != null) {
                        currentMenu = out[0];
                        submenu.clearChildren();
                        fadeInMenu();
                        //correctly offset the button
                        submenu.add().height((Core.graphics.getHeight() - Core.scene.marginTop - Core.scene.marginBottom - out[0].getY(Align.topLeft)) / Scl.scl(1f));
                        submenu.row();
                        buttons(submenu, b.submenu);
                    } else {
                        currentMenu = null;
                        fadeOutMenu();
                        b.runnable.run();
                    }
                }
            }).marginLeft(11f).get();
            out[0].update(() -> out[0].setChecked(currentMenu == out[0]));
            t.row();
        }
    }
    private static void fadeInMenu() {
        submenu.clearActions();
        submenu.actions(Actions.alpha(1f, 0.15f, Interp.fade));
    }

    private static void fadeOutMenu() {
        //nothing to fade out
        if (submenu.getChildren().isEmpty()) {
            return;
        }

        submenu.clearActions();
        submenu.actions(Actions.alpha(1f), Actions.alpha(0f, 0.2f, Interp.fade), Actions.run(() -> submenu.clearChildren()));
    }

    private static class Buttoni {
        final Drawable icon;
        final String text;
        final Runnable runnable;
        final Buttoni[] submenu;

        public Buttoni(String text, Drawable icon, Runnable runnable) {
            this.icon = icon;
            this.text = text;
            this.runnable = runnable;
            this.submenu = null;
        }

        public Buttoni(String text, Drawable icon, Buttoni... buttons) {
            this.icon = icon;
            this.text = text;
            this.runnable = () -> {
            };
            this.submenu = buttons;
        }
    }
}
