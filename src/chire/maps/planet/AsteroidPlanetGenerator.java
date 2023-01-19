package chire.maps.planet;

import arc.math.Rand;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import chire.content.CRBlocks;
import chire.util.WorldDef;
import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.game.Waves;
import mindustry.graphics.g3d.PlanetParams;
import mindustry.maps.generators.BlankPlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;

import static mindustry.Vars.state;
import static mindustry.Vars.world;

public class AsteroidPlanetGenerator extends BlankPlanetGenerator {
    public Block core = CRBlocks.asteroidcore;
    @Override
    public void generate(){
        seed = state.rules.sector.planet.id;
        int sx = width/2, sy = height/2;
        rand = new Rand(seed);

        Floor background = Blocks.snow.asFloor();
        Floor ground = Blocks.metalFloor5.asFloor();

        tiles.eachTile(t -> t.setFloor(background));

        WorldDef.getAreaTile(new Vec2(sx, sy), core.size + 6, core.size + 6).forEach(t -> {
            t.setFloor(ground);
        });

        pass((x, y) -> {
            block = Blocks.stoneWall;
        });
        pass((x, y) -> {
            if(noise(x, y, 3, 0.4f, 100f, 1f) > 0.59f){
                block = Blocks.stoneWall;
                floor = Blocks.stone;
            }
        });
        pass((x, y) -> {
            if(noise(x, y, 5, 0.6f, 80f, 1f) > 0.59f){
                block = Blocks.iceWall;
                floor = Blocks.ice;
            }
        });

        WorldDef.getAreaTile(new Vec2(sx, sy), 10, 10).forEach(t -> {
            t.setBlock(Blocks.air);
        });


        world.tile(sx + core.size / 2 + 3, sy + core.size / 2 + 3).setBlock(core, Team.sharded);


//        state.rules.planetBackground = new PlanetParams(){{
//            planet = sector.planet;
//            zoom = 1f;
//            camPos = new Vec3(1.2388899f, 1.6047299f, /*2.4758825f*/0);
//        }};

        state.rules.dragMultiplier = 0.7f; //yes, space actually has 0 drag but true 0% drag is very annoying
        state.rules.borderDarkness = true;
        state.rules.waves = true;

        state.rules.showSpawns = true;
        state.rules.spawns = Waves.generate(0.5f, rand, false, true, false);
    }

    @Override
    public int getSectorSize(Sector sector){
        return 800;
    }
}
