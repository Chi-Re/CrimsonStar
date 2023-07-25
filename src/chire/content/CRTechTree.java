package chire.content;

import mindustry.content.Planets;

import static chire.content.CRBlocks.planetaryAnnihilationDevice;
import static chire.content.CRBlocks.spaceStationLaunchPad;
import static chire.dependencies.EUTechTree.addToNode;
import static chire.dependencies.EUTechTree.node;
import static mindustry.content.Blocks.coreShard;

/**由于科技的操作过于麻烦，EUTechTree,防止依赖出错
 * WARNING universecore的依赖可能出错,注意使用频率*/
public class CRTechTree {
    public static void load(){
        addToNode(coreShard, () -> {
            node(planetaryAnnihilationDevice, ()->{
                node(spaceStationLaunchPad);
            });
        });
//        currentRoot(Planets.serpulo.techTree);
//        node(coreShard, planetaryAnnihilationDevice, p -> {
//
//        });
    }
}
