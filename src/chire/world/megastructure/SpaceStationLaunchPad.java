package chire.world.megastructure;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.scene.ui.Button;
import arc.scene.ui.TextArea;
import arc.scene.ui.layout.Table;
import arc.scene.ui.layout.WidgetGroup;
import arc.struct.EnumSet;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import chire.ChireJavaMod;
import chire.content.CREffects;
import chire.ui.StatLabel;
import chire.world.Block;
import chire.world.meta.CRStat;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Effect;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.ui.Bar;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.ui.dialogs.GameOverDialog;
import chire.ui.dialogs.LoadingFragment;
import mindustry.world.meta.BlockFlag;
import org.jetbrains.annotations.NotNull;

import static chire.ui.StatLabel.addStat;
import static chire.ui.StatLabel.addStats;
import static chire.world.meta.CRColor.gameYellow;
import static chire.world.meta.CRColor.getMessageRGBA;

public class SpaceStationLaunchPad extends Block {
    public TextureRegion baseRegion;
    public TextureRegion podRegion;
    public Button.ButtonStyle style;
    public Effect effect = Fx.none;
    public Item[] needItems = {Items.copper};
    public float launchTime = 1f;

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
        //cs
        baseRegion = ChireJavaMod.Load("launch-pad");
        podRegion = ChireJavaMod.Load("launchpod");
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

        stats.add(CRStat.headless, (table -> {
            table.add("否").row();
        }));
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar("冷却", (SpaceStationLaunchPadBulid build) -> new Bar(
                () -> "冷却",
                () -> Pal.ammo,
                () -> Mathf.clamp(build.launchCounter / launchTime)
        ));
    }


    public class SpaceStationLaunchPadBulid extends Building {
        public float launchCounter;
        public float NumberBombings = 1;
        public boolean cooling;

        @Override
        public void draw() {
            super.draw();
            Draw.rect(baseRegion, x, y);
            if (needItems()){
                Draw.color(1, 1, 1, Mathf.clamp(launchCounter / launchTime));
                //Draw.color(1F, 1F, 1f, (float) Math.sin((launchCounter / launchTime) * Math.PI * 3 / 2));
                Draw.rect(podRegion, x, y);

                //这寄把写的啥，算了，就这样吧
                if (launchCounter > 0 && Mathf.clamp(launchCounter / launchTime) != 1) {
                    Draw.color(Pal.accent);
                    Lines.lineAngleCenter(this.x + Mathf.sin(launchCounter *2f, 20f, size*6 / 2f), this.y, 90, size*6);
                }
            }
            Draw.reset();
        }

        /**讲真的这逻辑写的很烂，有时间改改<br>*应该没人看吧()*/
        @Override
        public void updateTile() {
            super.updateTile();
            if (!needItems()){
                cooling = false;
                //有点粗暴，但我喜欢()
                launchCounter = 0f;
            } else {
                if (launchCounter >= launchTime) {
                    cooling = true;
                } else {
                    cooling = false;
                    launchCounter += edelta();
                }
            }
        }

        /**暂时使用，之后更改*/
        public boolean needItems(){
            return this.items().get(needItems[0]) != 0;
        }

        @Override
        public void buildConfiguration(@NotNull Table table) {
            table.button(Icon.bookOpen, () -> {
                BaseDialog dialog = new BaseDialog("控制面板");
                Color color = Color.white.cpy();

                dialog.cont.table(t -> {
                    t.pane(p -> {
                        p.margin(13f);
                        p.left().defaults().left();
                        p.setBackground(Styles.black3);



                        p.table(stats -> {
//                            addStat(stats, "测试", 1, 0.5f);
//                            addStat(stats, "测试", "字符串1", 0.5f);
//                            addStat(stats, "测试", false, 0.5f);
//                            addStat(stats, "测试", true, 0.5f);
                            //"测试1", 1, "测试2", "字符串1", "测试3", false, "测试4", true
                            addStats(stats,
                                    "测试1", 1,
                                    "测试2", "字符串1",
                                    "测试3", false,
                                    "测试4", true
                            );

                            stats.add("发射状态:").color(color).update(a -> a.setColor(color)).row();
//                            LoadingFragment loadingFragment = new LoadingFragment();
//                            loadingFragment.show("测试", gameYellow);
                        }).top().grow().row();
                    }).grow().pad(12).top();
                }).center().minWidth(370).maxSize(600, 550).grow().update(scrollPane -> {
                    color.a = (float) (Math.sin(Time.time * 0.1) * 0.5 + 0.5);
                });

                dialog.addCloseButton();
                dialog.show();
            });
        }

        public void lunch() {
//            if (amount > 0 && !open) {
            for (int i = 0; i < NumberBombings; i++) {
                CREffects.launch(100, 1, podRegion).at(this);
                //无降落动画
                Fx.launchPod.at(this);
                Effect.shake(3f, 3f, this);
            }
            arc.util.Time.run(50, () -> {
                this.configure(true);
            });
            effect.at(this.x, this.y);

            launchCounter = 0f;
//            }
        }

        @Override
        public boolean acceptItem(Building source, Item item) {
            for (Item item1 : needItems) {
                if (item1 == item && this.items().get(item1) < itemCapacity) {
                    return true;
                }
            }
            return false;
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
