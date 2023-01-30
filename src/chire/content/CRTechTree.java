package chire.content;

import mindustry.content.TechTree;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static void load(){
        CRPlanets.Asteroid.techTree = nodeRoot("小行星科技", CRBlocks.asteroidcore, () -> {
            node(CRBlocks.coiledduct, () -> {

            });
            node(CRBlocks.CRBseparator);
        });
        CRPlanets.desertplanet.techTree = nodeRoot("沙漠星球科技", CRBlocks.desertcore, () -> {
            node(CRBlocks.coiledduct, () -> {
                node(CRBlocks.mineralCollectors);
            });
        });
    }
}
