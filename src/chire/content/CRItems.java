package chire.content;

import mindustry.type.Item;
import mindustry.world.meta.Attribute;

public class CRItems {
    public static Item zhadan, icecube;
    public static void load(){
        zhadan = new Item("zhadan"){
            {
                radioactivity = 2.5F;
                explosiveness = 3.0F;
                charge = 15.0F;
            }
        };
        icecube = new Item("ice-cube"){{
            radioactivity = 0;
            explosiveness = 0.01f;
            charge = 0;
        }};
    }
}
