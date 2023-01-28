package chire.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.content.SerpuloTechTree;
import mindustry.content.TechTree;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.Item;
import mindustry.type.ItemStack;

import static mindustry.content.TechTree.node;
import static mindustry.content.TechTree.nodeRoot;

public class CRTechTree {
    public static TechTree.TechNode context = null;
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

    /**
     * @author EU模组
     */
    public static void addToNode(UnlockableContent p, Runnable c) {
        context = TechTree.all.find(t -> t.content == p);
        c.run();
    }
    //本来想偷懒直接写个用的，结果发现还是这样来的好，哎)(嘿
    //我直接进行一个工厂源码的转↓↓↓
    public static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    public static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    public static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives, Runnable children){
        TechTree.TechNode node = new TechTree.TechNode(context, content, requirements);
        if(objectives != null){
            node.objectives.addAll(objectives);
        }

        TechTree.TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    public static void node(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    public static void node(UnlockableContent block){
        node(block, () -> {});
    }

    public static void nodeProduce(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.add(new Objectives.Produce(content)), children);
    }

    public static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, new Seq<>(), children);
    }
}
