package chire.content;

import mindustry.content.TechTree;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static TechTree.TechNode context = null;
    public static void load(){
        CRPlanets.Asteroid.techTree = nodeRoot("小行星科技", CRBlocks.asteroidcore, () -> {
            node(CRBlocks.coiledduct, () -> {

            });
            node(CRBlocks.CRBseparator);
        });
    }
}
