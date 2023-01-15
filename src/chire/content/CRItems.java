package chire.content;

import mindustry.type.Item;

public class CRItems {
    public static Item zhadan;
    public static void load(){
        zhadan = new Item("zhadan"){
            {
                radioactivity = 2.5F;
                explosiveness = 3.0F;
                charge = 15.0F;
            }
        };
    }
}
