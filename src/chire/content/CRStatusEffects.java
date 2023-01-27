package chire.content;

import arc.graphics.Color;
import mindustry.type.StatusEffect;

public class CRStatusEffects {
    public static StatusEffect CRcorroded;

    public static void load(){
        CRcorroded = new StatusEffect("CRcorroded"){{
            color = Color.royal;
            damage = 0.5f;
            speedMultiplier = 0.4f;
        }};
    }
}
