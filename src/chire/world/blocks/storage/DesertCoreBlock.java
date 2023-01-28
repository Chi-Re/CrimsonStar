package chire.world.blocks.storage;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import arc.struct.IntFloatMap;
import arc.struct.IntSet;
import arc.struct.Seq;
import arc.util.Strings;
import arc.util.Time;
import arc.util.Tmp;
import chire.util.CRUtil;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Player;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;

public class DesertCoreBlock extends CoreBlock {
    public float powerOutput;
    public int range = 20;//14
    private static final IntSet taken = new IntSet();
    private static final IntFloatMap mendMap = new IntFloatMap();
    private static long lastUpdateFrame = -1;
    public DesertCoreBlock(String name) {
        super(name);
            outputsPower = true;
            consumesPower = false;
            hasPower = true;
    }

    @Override
    public boolean canBreak(Tile tile){
        return Vars.state.teams.cores(tile.team()).size > 1;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.basePowerGeneration, 2500, StatUnit.powerSecond);
        stats.add(Stat.range, range, StatUnit.blocks);
        stats.add(new Stat("shield", StatCat.function),"\uF7A9");
    }

    @Override
    public void setBars() {
        super.setBars();
            addBar("power", (CoreBuild e) -> new Bar(
                    () -> Core.bundle.format("bar.poweroutput", Strings.fixed(e.getPowerProduction() * 60, 1)),
                    () -> Pal.powerBar,
                    () -> 1
            ));
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;

        Drawf.dashSquare(Pal.accent, x + 0.5f, y + 0.5f, range * tilesize);
        indexer.eachBlock(Vars.player.team(), Tmp.r1.setCentered(x + 0.5f, y + 0.5f, range * tilesize), b -> true, t -> {
            Drawf.selected(t, Tmp.c1.set(Pal.accent).a(Mathf.absin(4f, 1f)));
        });
    }

    public class DesertCoreBuild extends CoreBuild {
        public Seq<Building> targets = new Seq<>();
        public Unit target;
        public float getPowerProduction(){
            return powerOutput / 60f;
        }
        @Override
        public void draw(){
            super.draw();
            Draw.rect(block.region, x, y);
            float radius = (range * tilesize) / 2f;

            Draw.z(Layer.shields);
            Draw.color(team.color, Color.white, 0);
            Fill.square(x, y, radius);
        }
        @Override
        public void updateTile(){
            iframes -= Time.delta;
            thrusterTime -= Time.delta/90f;

            //修复建筑
            targets.clear();
            taken.clear();
            indexer.eachBlock(team, Tmp.r1.setCentered(x, y, range * tilesize), b -> true, targets::add);
            float healAmount = Mathf.lerp(1f, 2, optionalEfficiency) * (12f / 60f);
            for(var build : targets){
                if (build.block().id != tile.block().id) {
//                    build.damage(-build.maxHealth()/10);
                    int pos = build.pos();
                    //TODO periodic effect
                    float value = mendMap.get(pos);
                    mendMap.put(pos, Math.min(Math.max(value, healAmount * edelta() * build.block.health / 100f), build.block.health - build.health));

                    if(value <= 0 && Mathf.chanceDelta(0.003f * build.block.size * build.block.size)){
                        Fx.regenParticle.at(build.x + Mathf.range(build.block.size * tilesize/2f - 1f), build.y + Mathf.range(build.block.size * tilesize/2f - 1f));
                    }
                }
            }
            if(lastUpdateFrame != state.updateId){
                lastUpdateFrame = state.updateId;

                for(var entry : mendMap.entries()){
                    var build = world.build(entry.key);
                    if(build != null){
                        build.heal(entry.value);
                        build.recentlyHealed();
                    }
                }
                mendMap.clear();
            }
            //修复单位
            target = Units.closest(team, x, y, (range * tilesize)/2f, Unit::damaged);
            if (target != null) {
                if (timer.get(3f) && target.health < target.maxHealth/10) {
                    if (target.maxHealth < 1000) {
                        target.heal(target.maxHealth);
                    } else {
                        target.heal(0.3f);
                    }
                }
            }
        }

        public void drawSelect() {
            super.drawSelect();
            Drawf.dashSquare(Pal.accent, x + 0.5f, y + 0.5f, range * tilesize);
            indexer.eachBlock(Vars.player.team(), Tmp.r1.setCentered(x, y, range * tilesize), b -> true, t -> {
                Drawf.selected(t, Tmp.c1.set(Pal.accent).a(Mathf.absin(4f, 1f)));
            });
        }
    }
}
