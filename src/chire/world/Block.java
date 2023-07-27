package chire.world;

import arc.struct.Seq;
import mindustry.type.Item;
import mindustry.type.ItemStack;

/**
 * @author 炽热S
 * 这是为了实现小功能设计的，方便使用方法
 */
public class Block extends mindustry.world.Block {
    public ItemStack stackItrm;


    public Block(String name) {
        super(name);
    }

    public void settingItem(ItemStack itemStack){
        this.stackItrm = itemStack;
    }

    /**请不要在意这奇怪的翻译(心虚)*/
    public boolean canDemand(){
        return false;
    }

    public Seq<Item> getAnyItem(ItemStack[] itemStack){
        Seq<Item> items = new Seq<>();
        for (var item : itemStack){
            items.add(item.item);
        }
        return items;
    }
    public Seq<Integer> getAnyInt(ItemStack[] itemStack){
        Seq<Integer> items = new Seq<>();
        for (var item : itemStack){
            items.add(item.amount);
        }
        return items;
    }
}
