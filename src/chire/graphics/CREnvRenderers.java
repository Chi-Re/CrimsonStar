package chire.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import chire.world.meta.CREnv;
import mindustry.graphics.Layer;
import mindustry.type.Weather;
import mindustry.world.meta.Env;

import static mindustry.Vars.renderer;
import static mindustry.Vars.state;
import static mindustry.type.Weather.drawParticles;

//TODO 失败,需要研究
public class CREnvRenderers {
    public static void init(){
        Core.assets.load("sprites/distortAlpha.png", Texture.class);

        renderer.addEnvRenderer(Env.scorching, () -> {
            Texture tex = Core.assets.get("sprites/distortAlpha.png", Texture.class);
            if(tex.getMagFilter() != Texture.TextureFilter.linear){
                tex.setFilter(Texture.TextureFilter.linear);
                tex.setWrap(Texture.TextureWrap.repeat);
            }

            //TODO layer looks better? should not be conditional
            Draw.z(state.rules.fog ? Layer.fogOfWar + 1 : Layer.weather - 1);
            Weather.drawNoiseLayers(tex, Color.scarlet, 1000f, 0.24f, 0.4f, 1f, 1f, 0f, 4, -1.3f, 0.7f, 0.8f, 0.9f);
            Draw.reset();
        });
    }
}
