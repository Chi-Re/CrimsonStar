package chire.world.megastructure;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Button;
import arc.scene.ui.layout.Table;
import arc.struct.EnumSet;
import arc.util.io.Reads;
import arc.util.io.Writes;
import chire.ChireJavaMod;
import chire.content.CREffects;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;
import mindustry.world.meta.BlockFlag;
import org.jetbrains.annotations.NotNull;

public class SpaceStationLaunchPad extends Block {
    public TextureRegion LVicon;
    public TextureRegion boomicon;
    public Button.ButtonStyle style;
    public Effect effect = Fx.none;

    public SpaceStationLaunchPad(String name) {
        super(name);
        hasItems = true;
        solid = destructible = update = configurable = true;
        sync = true;
        flags = EnumSet.of(BlockFlag.launchPad);
    }

    @Override
    public boolean outputsItems(){
        return false;
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

    public class SpaceStationLaunchPadBulid extends Building {
        public float launchCounter;
        public float NumberBombings = 1;

        @Override
        public void draw() {
            super.draw();
            Draw.rect(LVicon, x, y);
            Draw.color();
            Draw.rect(boomicon, x, y);
            Draw.reset();
        }

        @Override
        public void updateTile() {
            super.updateTile();
        }

        @Override
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
//            if (amount > 0 && !open) {
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
//            }
        }

        @Override
        public void write(Writes write){
            super.write(write);
            write.f(launchCounter);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);
            launchCounter = read.f();
        }
    }
}
