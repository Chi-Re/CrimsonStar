package chire.world.blocks.campaign;

import arc.scene.ui.layout.Table;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.blocks.campaign.LaunchPad;

import static mindustry.Vars.*;

//更改卫星围绕的星球,失败,进度无法保存
public class PlanetLaunchPad extends LaunchPad{
    public PlanetLaunchPad(String name) {
        super(name);
    }
    public class PlanetLaunchPadBuild extends LaunchPadBuild {
        @Override
        public void buildConfiguration(Table table){
//            if(!state.isCampaign() || net.client()){
//                deselect();
//                return;
//            }

            table.button(Icon.upOpen, Styles.cleari, () -> {
//                state.rules.sector.info.destination = onset.sector;
                //onset.sector
                state.rules.sector.planet.parent = Planets.serpulo;

                deselect();
            }).size(40f);
        }
    }
}
