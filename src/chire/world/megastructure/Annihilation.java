package chire.world.megastructure;

import arc.Events;
import arc.Graphics;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.Planet;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

import static chire.ChireJavaMod.Load;
import static chire.ChireJavaMod.getText;
import static mindustry.Vars.*;

public class Annihilation extends Block {
    public TextureRegion arrowRegion;

    public Annihilation(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 8000;
        configurable = true;
    }

    @Override
    public void load() {
        super.load();
        arrowRegion = Load("launch-arrow");
    }

    @Override
    public void init(){
        consumeItems(new ItemStack(Items.copper, 1));
        super.init();
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class AcceleratorBuild extends Building {
        public float heat, statusLerp;
        public Planet[] planets = {Planets.erekir, Planets.serpulo};

        @Override
        public void updateTile(){
            super.updateTile();
            heat = Mathf.lerpDelta(heat, efficiency, 0.05f);
            statusLerp = Mathf.lerpDelta(statusLerp, power.status, 0.05f);
        }

        @Override
        public void draw(){
            super.draw();

            for(int l = 0; l < 4; l++){
                float length = 7f + l * 5f;
                Draw.color(Tmp.c1.set(Pal.darkMetal).lerp(team.color, statusLerp), Pal.darkMetal, Mathf.absin(Time.time + l*50f, 10f, 1f));

                for(int i = 0; i < 4; i++){
                    float rot = i*90f + 45f;
                    Draw.rect(arrowRegion, x + Angles.trnsx(rot, length), y + Angles.trnsy(rot, length), rot + 180f);
                }
            }

            if(heat < 0.0001f) return;

            float rad = size * tilesize / 2f * 0.74f;
            float scl = 2f;

            Draw.z(Layer.bullet - 0.0001f);
            Lines.stroke(1.75f * heat, Pal.accent);
            Lines.square(x, y, rad * 1.22f, 45f);

            Lines.stroke(3f * heat, Pal.accent);
            Lines.square(x, y, rad, Time.time / scl);
            Lines.square(x, y, rad, -Time.time / scl);

            Draw.color(team.color);
            Draw.alpha(Mathf.clamp(heat * 3f));

            for(int i = 0; i < 4; i++){
                float rot = i*90f + 45f + (-Time.time /3f)%360f;
                float length = 26f * heat;
                Draw.rect(arrowRegion, x + Angles.trnsx(rot, length), y + Angles.trnsy(rot, length), rot + 180f);
            }

            Draw.reset();
        }

        @Override
        public Graphics.Cursor getCursor(){
            return !state.isCampaign() || efficiency <= 0f ? Graphics.Cursor.SystemCursor.arrow : super.getCursor();
        }

        @Override
        public void buildConfiguration(Table table){
            table.button(Icon.admin, Styles.cleari, () -> {
//                Table table1 = new Table();
//                table1.table(t -> {
//                }).center().minWidth(370).maxSize(600, 550).grow();

                BaseDialog dialog = new BaseDialog("选择摧毁行星");
                for (var planet : planets){
                    dialog.cont.button(getText("planet."+planet.name+".name"), () -> {

                    }).size(100f, 50f);
                    dialog.cont.row();
                }
                dialog.addCloseButton();
                dialog.show();
                deselect();
            }).size(40f);
        }

        @Override
        public int getMaximumAccepted(Item item){
            return itemCapacity;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return items.get(item) < getMaximumAccepted(item);
        }
    }
}

