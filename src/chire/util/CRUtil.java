package chire.util;

import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;

/**
 * @author lyr
 * 是的,这个方法是lyr写的,好用!
 */
public class CRUtil {

    /**
     * 会自动判断是否为炮塔,如果不是炮塔返回0
     * @param block
     * @return range
     */
    public static float getRange(Block block) {
        if (block instanceof Turret) {
            return ((Turret) block).range;
        }
        return 0f;
    }

}
