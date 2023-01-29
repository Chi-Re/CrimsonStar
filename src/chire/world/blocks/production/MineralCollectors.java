package chire.world.blocks.production;

import arc.math.geom.Geometry;
import arc.util.Tmp;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Separator;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatCat;

import static mindustry.Vars.world;

//Mineral collectors 矿物收集器
public class MineralCollectors extends Separator {
    public MineralCollectors(String name) {
        super(name);
        hasLiquids = false;
    }
    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.tiles,"\uF6CB\uF6C7\uF6CA\uF6C9\uF6C8\uF67D");
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
}
