package chire.world.megastructure;

import arc.Core;
import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.gen.Icon;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.ui.dialogs.PlanetDialog;
import mindustry.world.blocks.campaign.Accelerator;

import static chire.ui.PopDialogs.showToast;
import static mindustry.Vars.state;
import static mindustry.Vars.ui;

public class Annihilation extends Accelerator {
    public Annihilation(String name) {
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

    public class AnnihilationBuild extends AcceleratorBuild {

        @Override
        public void buildConfiguration(Table table){
            //ui.showInfo("This block has been removed from the tech tree as of v7, and no longer has a use.\n\nWill it ever be used for anything? Who knows.");

//            ui.planet.showPlanetLaunch(state.rules.sector, sector -> {
//                //TODO cutscene, etc...
//
//                //TODO should consume resources based on destination schem
//                consume();
//
//                universe.clearLoadoutInfo();
//                universe.updateLoadout(sector.planet.generator.defaultLoadout.findCore(), sector.planet.generator.defaultLoadout);

//                Planet planet = Planets.erekir;
//                planet.alwaysUnlocked = false;
//                planet.accessible = false;
//                planet.visible = false;
//            });

//            Planet planet = state.rules.sector.planet;
//            state.gameOver = true;
//            Events.fire(new EventType.GameOverEvent(state.rules.waveTeam));
//            Planet canaccessible;
//            for (Planet planet : Planets.sun.children) {
//                if (planet.accessible || planet.visible){
//                    canaccessible = planet;
//                }
//            }
//            ui.planet.showPlanetLaunch(state.rules.sector, s -> {
//                ui.planet.show();
            Planet planet = state.rules.sector.planet;
//                for (var core : player.team().cores().copy()) {
//                    core.kill();
//                }
            for (Sector sector : planet.sectors) {
                //Events.fire(new EventType.SectorLoseEvent(sector));
                sector.info.items.clear();
                sector.info.damage = 1f;
                sector.info.hasCore = false;
                sector.info.production.clear();//弹窗
            }

            planet.alwaysUnlocked = false;
            planet.accessible = false;
            planet.visible = false;
            ui.planet.state.planet = Planets.erekir;
            showToast(Icon.warning, Core.bundle.format("planet.lost", planet.name));

//                Events.fire(EventType.Trigger.acceleratorUse);
//            });
        }
    }
}
