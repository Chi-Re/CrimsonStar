package chire.world.blocks.campaign;

import arc.Events;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.campaign.Accelerator;

import static mindustry.Vars.*;
import static mindustry.Vars.universe;

public class NewAccelerator extends Accelerator {


    public NewAccelerator(String name){
        super(name);
        update = true;
        solid = true;
        hasItems = true;
        itemCapacity = 8000;
        configurable = true;
    }

    @Override
    public void init(){
    }

    @Override
    public boolean outputsItems(){
        return false;
    }

    public class AcceleratorBuild extends Building {
        public float heat, statusLerp;

        @Override
        public void updateTile() {
            super.updateTile();
            heat = Mathf.lerpDelta(heat, efficiency, 0.05f);
            statusLerp = Mathf.lerpDelta(statusLerp, power.status, 0.05f);
        }

        @Override
        public void draw() {
            super.draw();

            for (int l = 0; l < 4; l++) {
                float length = 7f + l * 5f;
                Draw.color(Tmp.c1.set(Pal.darkMetal).lerp(team.color, statusLerp), Pal.darkMetal, Mathf.absin(Time.time + l * 50f, 10f, 1f));

                for (int i = 0; i < 4; i++) {
                    float rot = i * 90f + 45f;
                    Draw.rect(arrowRegion, x + Angles.trnsx(rot, length), y + Angles.trnsy(rot, length), rot + 180f);
                }
            }

            if (heat < 0.0001f) return;

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

            for (int i = 0; i < 4; i++) {
                float rot = i * 90f + 45f + (-Time.time / 3f) % 360f;
                float length = 26f * heat;
                Draw.rect(arrowRegion, x + Angles.trnsx(rot, length), y + Angles.trnsy(rot, length), rot + 180f);
            }

            Draw.reset();
        }

        @Override
        public void buildConfiguration(Table table){
            deselect();

            if(!state.isCampaign() || efficiency <= 0f) return;

            ui.showInfo("This block has been removed from the tech tree as of v7, and no longer has a use.\n\nWill it ever be used for anything? Who knows.");

            ui.planet.showPlanetLaunch(state.rules.sector, sector -> {
                //TODO cutscene, etc...

                //TODO should consume resources based on destination schem
                consume();

                universe.clearLoadoutInfo();
                universe.updateLoadout(sector.planet.generator.defaultLoadout.findCore(), sector.planet.generator.defaultLoadout);
            });

            Events.fire(EventType.Trigger.acceleratorUse);
        }
    }
}
