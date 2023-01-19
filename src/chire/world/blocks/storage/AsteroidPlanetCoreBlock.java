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

public class AsteroidPlanetCoreBlock extends CoreBlock {
    public float powerOutput;
    public AsteroidPlanetCoreBlock(String name) {
        super(name);
        outputsPower = true;
        consumesPower = false;
        hasPower = true;
    }

    @Override
    public boolean canBreak(Tile tile){
        return Vars.state.teams.cores(tile.team()).size > 1;
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.basePowerGeneration, 2500, StatUnit.powerSecond);
    }

    @Override
    public void setBars() {
        super.setBars();
        addBar("power", (CoreBuild e) -> new Bar(
                () -> Core.bundle.format("bar.poweroutput", Strings.fixed(e.getPowerProduction() * 60, 1)),
                () -> Pal.powerBar,
                () -> 1
        ));
    }

    public class AsteroidPlanetBuild extends CoreBuild {
        public float getPowerProduction(){
            return powerOutput / 60f;
        }
    }
}
