package chire.content;

import mindustry.content.Planets;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("blackhole", CRBlocks.CRinterplanetaryAccelerator, () -> {
            node(CRBlocks.ModelCcore, () -> {

            });
        });
    }
}
