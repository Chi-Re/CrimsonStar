package chire.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.world.meta.Attribute;

public class CRItems {
    public static Item zhadan, icecube, iceresidue;
    public static final Seq<Item> AsteroidPlanetItems = new Seq<>();
    public static void load(){
        zhadan = new Item("zhadan"){
            {
                radioactivity = 2.5F;
                explosiveness = 3.0F;
                charge = 15.0F;
            }
        };
        icecube = new Item("冰块"){{
            radioactivity = 0f;
            explosiveness = 0f;
            charge = 0f;
        }};
        iceresidue = new Item("冰渣"){{
            radioactivity = 0f;
            explosiveness = 0f;
            charge = 0f;
        }};

        AsteroidPlanetItems.add(
                icecube, iceresidue
        ).add(Items.serpuloItems);
    }
}
