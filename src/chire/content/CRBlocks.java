package chire.content;

import chire.world.megastructure.Annihilation;
import chire.world.megastructure.SpaceStationLaunchPad;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;

public class CRBlocks {
    public static Block planetaryAnnihilationDevice;//Megastructure
    public static Block spaceStationLaunchPad;

    public static void load(){
        //行星湮灭装置
        planetaryAnnihilationDevice = new Annihilation("planetary-annihilation-device"){{
            requirements(Category.effect, BuildVisibility.campaignOnly, with(Items.copper, 1));
            researchCostMultiplier = 0.1f;
            size = 7;
            hasPower = true;
            buildCostMultiplier = 0.5f;
            scaledHealth = 80;
            consumePower(1f);
        }};

        spaceStationLaunchPad = new SpaceStationLaunchPad("space-station-launch-pad"){{
            requirements(Category.effect, BuildVisibility.campaignOnly, with(Items.copper, 1));
            size = 3;
            itemCapacity = 100;
        }};
    }
}
