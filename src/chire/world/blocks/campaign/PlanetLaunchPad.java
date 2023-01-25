package chire.world.blocks.campaign;

import arc.scene.ui.layout.Table;
import chire.content.CRPlanets;
import chire.util.CRUtil;
import chire.util.STTFile;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.blocks.campaign.LaunchPad;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static mindustry.Vars.*;

//更改卫星围绕的星球,失败,进度无法保存
public class PlanetLaunchPad extends LaunchPad{
    private static final chire.util.STTFile sttFile = new chire.util.STTFile();
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
                File serpulofile = CRUtil.SFile("planets/serpulo.stt");
                if (serpulofile.exists()) {
                    serpulofile.delete();
                    state.rules.sector.planet.parent = CRPlanets.desertplanet;
                } else {
                    sttFile.SttFile(serpulofile);
                }

                deselect();
            }).size(40f);
        }
    }
}
