package chire.world.blocks.distribution;

import chire.content.CRBlocks;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Conveyor;

public class CoiledDuct extends Conveyor {
    public CoiledDuct(String name) {
        super(name);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for (int i = 0; i < 4; i++) {
            Tile other = tile.nearby(i);
            if (other != null && other.block().id == id){
                return true;
            }
            if (other != null && other.block().id == CRBlocks.desertcore.id){
                return true;
            }
        }
        return false;
    }
}
