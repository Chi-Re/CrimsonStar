package chire.world.blocks.distribution;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import chire.content.CRBlocks;
import mindustry.content.Blocks;
import mindustry.entities.units.BuildPlan;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Duct;

public class CoiledDuct extends Duct {
    public CoiledDuct(String name) {
        super(name);
        hasPower = true;
        consumesPower = true;
        conductivePower = true;
        consumePower(0.1f);
    }

    //是的,管道本来是需要连接核心的,但这很离谱且没意思
//    @Override
//    public boolean canPlaceOn(Tile tile, Team team, int rotation){
//        for (int i = 0; i < 4; i++) {
//            Tile other = tile.nearby(i);
//            if (other != null && other.block().id == id){
//                return true;
//            }
//            if (other != null && other.block().id == CRBlocks.desertcore.id){
//                return true;
//            }
//        }
//        return false;
//    }

//    public class CoiledDuctBuild extends ConveyorBuild{
//        @Override
//        public void updateTile(){
////            Tile other1 = tile.nearby(1);
////            Tile other2 = tile.nearby(2);
////            Tile other3 = tile.nearby(3);
////            Tile other4 = tile.nearby(4);
////            if (other1 != null && other2 != null && other3 != null && other4 != null) {
////                if (other1.block().id != id &&
////                        other2.block().id != id &&
////                        other3.block().id != id &&
////                        other4.block().id != id) {
////                    if (other1.block().id != CRBlocks.desertcore.id &&
////                            other2.block().id != CRBlocks.desertcore.id &&
////                            other3.block().id != CRBlocks.desertcore.id &&
////                            other4.block().id != CRBlocks.desertcore.id) {
////                        this.kill();
////                    }
////                }
////            }
////            int q = 0;
////            for (int i = 0; i < 4; i++) {
////                Tile other = tile.nearby(i);
////                if (other != null && other.block().id != CRBlocks.desertcore.id) {
////                    if (other.block().id != CRBlocks.coiledduct.id) {
////                        q++;
////                    }
////                }
////                if(q == 4){
////                    this.kill();
////                }
////            }
//        }
//    }
}
