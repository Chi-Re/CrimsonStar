package chire.world.blocks.storage;

import arc.Core;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class DesertCoreBlock extends CoreBlock {
    public float powerOutput;
    public DesertCoreBlock(String name) {
        super(name);
        if (powerOutput != 0) {
            outputsPower = true;
            consumesPower = false;
            hasPower = true;
        }
    }

    @Override
    public boolean canBreak(Tile tile){
        return Vars.state.teams.cores(tile.team()).size > 1;
    }

    @Override
    public void setStats(){
        super.setStats();
        if (powerOutput != 0) {
            stats.add(Stat.basePowerGeneration, 2500, StatUnit.powerSecond);
        }
    }

    @Override
    public void setBars() {
        super.setBars();
        if (powerOutput != 0) {
            addBar("power", (CoreBuild e) -> new Bar(
                    () -> Core.bundle.format("bar.poweroutput", Strings.fixed(e.getPowerProduction() * 60, 1)),
                    () -> Pal.powerBar,
                    () -> 1
            ));
        }
    }

    public class DesertCoreBuild extends CoreBuild {
        public float getPowerProduction(){
            return powerOutput / 60f;
        }
    }
}
