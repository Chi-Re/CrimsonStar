package chire.world.megastructure;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Button;
import arc.scene.ui.TextArea;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import chire.ChireJavaMod;
import chire.content.CRBullets;
import chire.content.CREffects;
import chire.world.meta.CRStat;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import org.jetbrains.annotations.NotNull;

//空间站发射台SpaceStationLaunchPad
public class SpaceStationLaunchPadNO extends Block {
    public float Time = 3 * 60;
    public BulletType bullet = CRBullets.Explode;
    public float Range = 20;
    public Button.ButtonStyle style;
    public Effect effect = Fx.none;
    public Item[] acitems = {Items.copper};
    public Sound attackSound = Sounds.explosionbig;
    public TextureRegion LVicon;
    public TextureRegion boomicon;

    public SpaceStationLaunchPadNO(String name) {
        super(name);
        hasPower = true;
        hasItems = true;
        solid = destructible = update = configurable = true;
        sync = true;
        group = BlockGroup.turrets;
        envEnabled = Env.any;

        config(Boolean.class, (SpaceStationBulid tile, Boolean value) -> {
            tile.open = value;
        });
    }

    @Override
    public void load() {
        super.load();
        LVicon = ChireJavaMod.Load("launch-pad");
        boomicon = ChireJavaMod.Load("launchpod");
    }

    @Override
    public void init() {
        super.init();
        if (!Vars.headless) {
            this.style = Core.scene.getStyle(Button.ButtonStyle.class);
        }
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.input, (table -> {
            for (Item item : acitems) {
                table.image(item.uiIcon);
            }
        }));
        stats.add(CRStat.headless, (table -> {
            table.add("否").row();
        }));
    }

    public class SpaceStationBulid extends Building {
        public int amount;
        public float time = Time;
        public float xxx, yyy;
        public boolean open;
        public float NumberBombings = 1;

        @Override
        public void updateTile() {
            super.updateTile();
            for (Item item : acitems) {
                amount = this.items().get(item);
            }
            if (efficiency > 0 && open) {
                time -= this.edelta();
                if (time <= 0) {
                    time = Time;
                    if (bullet != null && open) {
                        for (int z = 0; z < NumberBombings; z++) {
                            for (int i = 0; i < amount; i++) {
                                bullet.create(this, xxx, yyy, Mathf.random(360));
                                xxx += Mathf.random(-Range, Range);
                                yyy += Mathf.random(-Range, Range);
                            }
                            attackSound.at(xxx, yyy);
                        }
                        open = false;
                    }
                }
            }
        }


        public void buildConfiguration(@NotNull Table table) {
            table.button(Icon.bookOpen, () -> {
                BaseDialog dialog = new BaseDialog("控制面板");
                dialog.cont.pane(inner -> inner.pane(table1 -> table1.pane(t -> {
                    t.table(style.checked, (inde) -> {
                        inde.button("确定", () -> {
                            lunch();
                            dialog.hide();
                        }).size(300, 50f).row();

                        dialog.addCloseListener();
                    }).left().color(Pal.gray).size(300, 300).fillX().row();
                }).left().fillX().growY().row()).left().growY().center().row()).grow();
                dialog.show();
            });
        }

        public void lunch() {
            if (amount > 0 && !open) {
                for (int i = 0; i < NumberBombings; i++) {
                    CREffects.launch(100, 1, boomicon).at(this);
                    //无降落动画
                    Fx.launchPod.at(this);
                    Effect.shake(3f, 3f, this);
                }
                arc.util.Time.run(50, () -> {
                    this.configure(true);
                });
                effect.at(this.x, this.y);
            }
        }

        @Override
        public void draw() {
            super.draw();
            Draw.rect(LVicon, x, y);
            if (!open) {
                Draw.color();
                Draw.rect(boomicon, x, y);
            }
            Draw.reset();
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            for (Item item1 : acitems) {
                if (item1 == item && this.items().get(item1) < itemCapacity) {
                    return true;
                }
            }
            return false;
        }
    }
}
