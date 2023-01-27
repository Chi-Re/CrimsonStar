package chire.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.content.SerpuloTechTree;
import mindustry.game.Objectives;
import mindustry.type.Item;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("测试塞普罗科技", CRBlocks.CRinterplanetaryAccelerator, () -> {
            node(CRBlocks.ModelCcore, () -> {

            });
            node(CRBlocks.CRlaunchPad, Seq.with(new Objectives.Research(Items.copper)), () -> {

            });
            node(CRBlocks.CRpowerSource, () -> {

            });
            node(CRBlocks.CRitemSource, () -> {
            });
//            node(CRBlocks.CRPlaunchPad, () -> {
//
//            });
        });
        CRPlanets.Asteroid.techTree = nodeRoot("小行星科技", CRBlocks.asteroidcore, () -> {
            node(CRBlocks.CRBseparator);
        });
    }
}
