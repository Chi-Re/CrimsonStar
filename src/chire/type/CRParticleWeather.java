package chire.type;

import arc.math.geom.Geometry;
import arc.util.Time;
import arc.util.Tmp;
import arc.util.noise.Noise;
import chire.content.CRBlocks;
import mindustry.content.Blocks;
import mindustry.content.StatusEffects;
import mindustry.gen.Groups;
import mindustry.gen.Sounds;
import mindustry.gen.WeatherState;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.Tile;

import static mindustry.Vars.*;

//TODO 失败,无法将建筑加入伤害
public class CRParticleWeather extends ParticleWeather {
    private float damageTimer;
    public CRParticleWeather(String name) {
        super(name);
    }

    public void updateEffect(WeatherState state){
        if(status != StatusEffects.none){
            if(state.effectTimer <= 0){
                state.effectTimer = statusDuration - 5f;

                Groups.unit.each(u -> {
                    if(u.checkTarget(statusAir, statusGround)){
                        u.apply(status, statusDuration);
                    }
                });
//                Groups.build.each(b -> {
//                    Tile other = world.tile((int) u.x, (int) u.y);
////                    other.apply(status, statusDuration);
//                    for (int i = 0; i < 4; i++) {
//                        Tile other1 = other.nearby(i);
//                        if (other1 != null && other1.block().id == Blocks.sandWall.id){
//                            other.setBlock(Blocks.air);
//                        }
//                    }
//                    b.damage(10);
//                });
            }else{
                state.effectTimer -= Time.delta;
            }
            if(damageTimer <= 0) {
                damageTimer = statusDuration - 5f;
                Groups.build.each(b -> {
                    if (b.block().id != CRBlocks.desertcore.id) {
                        b.damage(b.maxHealth()/10);
                    }
//                    if (b.block().buildType == Blocks.conveyor.buildType) {
//                        b.damage(1000);
//                    }
//                    Tile other = b.tile;
//                    int ixy = 4;
//                    float getx = b.x -ixy, gety = b.y - ixy;
//                    if (getblocks(ixy, getx, gety)) {
//                        b.damage(10);
//                    }
                });
            }else {
                damageTimer -= Time.delta;
            }
        }

        if(!headless && sound != Sounds.none){
            float noise = soundVolOscMag > 0 ? (float)Math.abs(Noise.rawNoise(Time.time / soundVolOscScl)) * soundVolOscMag : 0;
            control.sound.loop(sound, Math.max((soundVol + noise) * state.opacity, soundVolMin));
        }
    }

    public boolean getblocks(int ixy, float getx, float gety){
        boolean asd = true;
        for (int ix = 0; ix < ixy; ix++) {
//        Tile other1 = other.nearby(i);
//        if (other1 != null && other1.block().id == Blocks.sandWall.id){
//            b.damage(10);
//        }
            for (int iy = 0; iy < ixy; iy++) {
                Tile other = world.tile((int) (getx + iy), (int) (gety + iy));
                if (other != null && other.block().id == Blocks.sandWall.id) {
                    asd = false;
                }
            }
        }
        return asd;
    }
}
