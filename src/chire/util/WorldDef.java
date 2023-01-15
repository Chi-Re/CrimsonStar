package chire.util;

import arc.math.geom.Vec2;
import arc.struct.Seq;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;

import static mindustry.Vars.world;

/** @author zeutd
 * 太好用了,很感谢这位作者
 */
public class WorldDef {
    public static boolean toBlock(Building block, Building other){
        return !other.block().rotate || (block.relativeTo(other) + 2) % 4 == other.rotation;
    }
    public static Seq<Item> listItem(Seq<Item> array){
        Seq<Item> result = new Seq<>();
        array.forEach(a -> {
            if(!result.contains(a) && a != null){
                result.add(a);
            }
        });
        return result;
    }

    public static Seq<Tile> getAreaTile(Vec2 pos, int width, int height){
        Seq<Tile> tilesGet = new Seq<>();
        int dx = (int) pos.x;
        int dy = (int) pos.y;
        for (int ix = 0; ix < width; ix ++){
            for (int iy = 0; iy < height; iy ++){
                Tile other = world.tile(ix + dx + 1, iy + dy + 1);
                tilesGet.add(other);
            }
        }
        return tilesGet;
    }
}
