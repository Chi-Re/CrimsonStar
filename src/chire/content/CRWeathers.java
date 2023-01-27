package chire.content;

import arc.graphics.Color;
import arc.util.Time;
import chire.type.CRParticleWeather;
import mindustry.content.StatusEffects;
import mindustry.gen.Sounds;
import mindustry.type.Weather;
import mindustry.type.weather.ParticleWeather;
import mindustry.world.meta.Attribute;

public class CRWeathers {
    public static Weather CRsandstorm;
    public static void load(){
        CRsandstorm = new CRParticleWeather("CR-sandstorm"){{
            color = noiseColor = Color.valueOf("f7cba4");
            particleRegion = "particle";
            drawNoise = true;
            useWindVector = true;
            sizeMax = 140f;
            sizeMin = 70f;
            minAlpha = 0f;
            maxAlpha = 0.2f;
            density = 1500f;
            baseSpeed = 5.4f;
            attrs.set(Attribute.light, -0.1f);
            attrs.set(Attribute.water, -0.1f);
            opacityMultiplier = 0.35f;
            force = 0.1f;
            sound = Sounds.wind;
            soundVol = 0.8f;
            duration = 700f * Time.toMinutes;
            status = CRStatusEffects.CRcorroded;
        }};
    }
}
