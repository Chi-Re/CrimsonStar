package chire.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Objectives;
import mindustry.type.Item;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static void load(){
        Planets.serpulo.techTree = nodeRoot("blackhole", CRBlocks.CRinterplanetaryAccelerator, () -> {
            node(CRBlocks.ModelCcore, () -> {

            });
            node(CRBlocks.CRlaunchPad, Seq.with(new Objectives.Research(Items.copper)), () -> {

            });
            node(CRBlocks.CRpowerSource, () -> {

            });
            node(CRBlocks.CRitemSource, () -> {

            });
            node(CRBlocks.CRPlaunchPad, () -> {

            });
        });
    }
}
