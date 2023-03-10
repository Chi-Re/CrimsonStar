package chire.world.blocks.campaign;

import arc.scene.ui.layout.Table;
import mindustry.content.SectorPresets;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.blocks.campaign.LaunchPad;

import static mindustry.Vars.*;
import static mindustry.Vars.state;
import static mindustry.content.SectorPresets.onset;

public class SunLaunchPad extends LaunchPad {
//    public CRPlanetDialog CRPD = new CRPlanetDialog();
    public SunLaunchPad(String name) {
        super(name);
    }

    public class SunLaunchPadBuild extends LaunchPadBuild {
        @Override
        public void buildConfiguration(Table table){
            if(!state.isCampaign() || net.client()){
                deselect();
                return;
            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
//                state.rules.sector.info.destination = onset.sector;
                //onset.sector
                ui.planet.showSelect(onset.sector, other -> {
                    if(state.isCampaign() && other.planet == state.rules.sector.planet){
                        state.rules.sector.info.destination = other;
                    }
                });
                deselect();
            }).size(40f);
        }
    }
}
