package chire.world.blocks.campaign;

import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.blocks.campaign.LaunchPad;

import static mindustry.Vars.*;
import static mindustry.Vars.state;

public class SunLaunchPad extends LaunchPad {
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
                ui.planet.showSelect(state.rules.sector, other -> {
                    if(state.isCampaign() && other.planet == state.rules.sector.planet){
                        state.rules.sector.info.destination = other;
                    }
                });
                deselect();
            }).size(40f);
        }
    }
}
