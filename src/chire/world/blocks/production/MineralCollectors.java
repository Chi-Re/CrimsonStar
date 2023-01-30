package chire.world.blocks.production;

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
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.entities.Units;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Separator;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;
import static mindustry.Vars.tilesize;

//Mineral collectors 矿物收集器
public class MineralCollectors extends Separator {
    public int range = 10;//14
    public float powerOutput;
    private static final IntSet taken = new IntSet();
    private static final IntFloatMap mendMap = new IntFloatMap();
    private static long lastUpdateFrame = -1;
    public MineralCollectors(String name) {
        super(name);
        hasLiquids = false;
        outputsPower = true;
        consumesPower = false;
        hasPower = true;
    }
    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.basePowerGeneration, 200f, StatUnit.powerSecond);
        stats.add(Stat.range, range, StatUnit.blocks);
        stats.add(Stat.tiles,"\uF6CB\uF6C7\uF6CA\uF6C9\uF6C8\uF67D");
    }
    @Override
    public void setBars() {
        super.setBars();
        addBar("power", (SeparatorBuild e) -> new Bar(
                () -> Core.bundle.format("bar.poweroutput", Strings.fixed(e.getPowerProduction() * 60, 1)),
                () -> Pal.powerBar,
                () -> 1
        ));
}
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        int tx = tile.x;
        int ty = tile.y;
        Tile other1 = world.tile(tx + 1, ty + 1),
                other2 = world.tile(tx - 1, ty + 1),
                other3 = world.tile(tx + 1, ty - 1),
                other4 = world.tile(tx - 1, ty - 1);
        Tile[] other = new Tile[]{other1, other2, other3, other4};
        Block[] blocks = new Block[]{
                Blocks.rhyoliteVent, Blocks.carbonVent, Blocks.arkyicVent, Blocks.yellowStoneVent,
                Blocks.redStoneVent, Blocks.crystallineVent
        };
        for (Tile oth : other) {
            if (oth.floor().id != blocks[1].id && oth.floor().id != blocks[2].id && oth.floor().id != blocks[3].id && oth.floor().id != blocks[4].id && oth.floor().id != blocks[5].id && oth.floor().id != blocks[0].id){
                return false;
            }
        }
        return true;
    }

    public class MineralCollectorBuild extends SeparatorBuild {
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
            super.updateTile();
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
