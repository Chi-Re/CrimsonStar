package InitiateUI.CRUI;

import arc.Core;
import arc.Events;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.style.Drawable;
import arc.scene.ui.Button;
import arc.scene.ui.ImageButton;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.util.Align;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.Fonts;
import mindustry.ui.MobileButton;
import mindustry.ui.Styles;

import static mindustry.Vars.*;
import static mindustry.gen.Tex.discordBanner;
import static mindustry.gen.Tex.infoBanner;

//TODO 测试不可用,似乎是我的问题,会有解决方法的
public class CRMenu {
    private static Table container = Core.scene.table();
    private static Table submenu;
    private static Button currentMenu;
    private static mindustry.graphics.MenuRenderer renderer2;

    public static void init() {
        if (!Vars.headless) {
            Vars.ui.menufrag = null;
            Events.on(EventType.ClientLoadEvent.class, e -> {
                build(ui.menuGroup);
            });
        }
    }


    public static void build(Group parent) {
        Group group = new WidgetGroup();
        group.setFillParent(true);
        group.visible(() -> !ui.editor.isShown());
        parent.addChild(group);
        parent = group;
        parent.fill(c -> {
            container = c;
            c.name = "menu container";
            if (!mobile) {
                buildDesktop();
                Events.on(EventType.ResizeEvent.class, event -> buildDesktop());
            } else {
                buildMobile();
                Events.on(EventType.ResizeEvent.class, event -> buildMobile());
            }
        });
        parent.fill(c -> c.bottom().right().button(Icon.discord, new ImageButton.ImageButtonStyle() {{
            up = discordBanner;
        }}, ui.discord::show).marginTop(9f).marginLeft(10f).tooltip("@discord").size(84, 45).name("discord"));
        if (mobile) {
            parent.fill(c -> c.bottom().left().button("", new TextButton.TextButtonStyle() {{
                font = Fonts.def;
                fontColor = Color.white;
                up = infoBanner;
            }}, ui.about::show).size(84, 45).name("info"));
        } else if (becontrol.active()) {
            parent.fill(c -> c.bottom().right().button("检查更新", Icon.refresh, () -> {
                ui.loadfrag.show();
                becontrol.checkUpdate(result -> {
                    ui.loadfrag.hide();
                    if (!result) {
                        ui.showInfo("@be.noupdates");
                    }
                });
            }).size(200, 60).name("becheck").update(t -> {
                t.getLabel().setColor(becontrol.isUpdateAvailable() ? Tmp.c1.set(Color.white).lerp(Pal.accent, Mathf.absin(5f, 1f)) : Color.white);
            }));
        }
    }

    public static void buildMobile() {
        container.clear();
        container.name = "buttons";
        container.setSize(Core.graphics.getWidth(), Core.graphics.getHeight());

        float size = 120f;
        container.defaults().size(size).pad(5).padTop(4f);
        //container.top().add(asdtest);
        container.marginTop(60f);

        MobileButton play = new MobileButton(Icon.play, "@campaign", () -> checkPlay(ui.planet::show)), custom = new MobileButton(Icon.rightOpenOut, "@customgame", () -> checkPlay(ui.custom::show)), maps = new MobileButton(Icon.download, "@loadgame", () -> checkPlay(ui.load::show)), join = new MobileButton(Icon.add, "@joingame", () -> {
            checkPlay(ui.join::show);
        }), editor = new MobileButton(Icon.terrain, "@editor", () -> checkPlay(ui.maps::show)), tools = new MobileButton(Icon.settings, "@settings", ui.settings::show), mods = new MobileButton(Icon.book, "@mods", ui.mods::show), exit = new MobileButton(Icon.exit, "@quit", () -> Core.app.exit()),
                set = new MobileButton(Icon.settings, "设置", () -> checkPlay(ui.planet::show));

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
                table.add(set);
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
                table.add(set);
            }).colspan(2);
        }
    }

    private static void buildDesktop() {
        container.clear();
        container.setSize(Core.graphics.getWidth(), Core.graphics.getHeight());
        float width = 230f;
        Drawable background = Styles.black6;
        container.left();
        container.add().width(Core.graphics.getWidth() / 10f);
        container.table(background, t -> {
            t.defaults().width(width).height(70f);
            t.name = "buttons";
            buttons(t, new Buttoni("@play", Icon.play, new Buttoni("@campaign", Icon.play, () -> checkPlay(ui.planet::show)), new Buttoni("@joingame", Icon.add, () -> {
                        checkPlay(ui.join::show);
                    }), new Buttoni("@customgame", Icon.terrain, () -> checkPlay(ui.custom::show)), new Buttoni("@loadgame", Icon.download, () -> checkPlay(ui.load::show))), new Buttoni("@database.button", Icon.menu, new Buttoni("@schematics", Icon.paste, ui.schematics::show), new Buttoni("@database", Icon.book, ui.database::show), new Buttoni("@about.button", Icon.info, ui.about::show)), new Buttoni("@editor", Icon.terrain, () -> checkPlay(ui.maps::show)), steam ? new Buttoni("@workshop", Icon.steam, platform::openWorkshop) : null,
            new Buttoni("设置", Icon.settings, () -> checkPlay(ui.planet::show)));

        }).width(width).growY();

        container.table(background, t -> {
            submenu = t;
            t.name = "submenu";
            t.color.a = 0f;
            t.top();
            t.defaults().width(width).height(70f);
            t.visible(() -> !t.getChildren().isEmpty());

        }).width(width).growY();

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


    private static void checkPlay(Runnable run) {

        if (!mods.hasContentErrors()) {
            run.run();
        } else {
            ui.showInfo("@mod.noerrorplay");
        }
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
