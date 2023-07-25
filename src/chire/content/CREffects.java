package chire.content;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Interp;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

public class CREffects {
    /**发射动画*/
    public static Effect launch(float lifetime, float size, TextureRegion region) {
        return new Effect(lifetime, e -> {
            //向上飞行
            float alpha = e.fout(Interp.pow5Out);
            float scale = (1.0F - alpha) * size + 1.0F;
            float cx = e.x + e.fin(Interp.pow2In) * (12.0F + Mathf.randomSeedRange(e.id + 3, 5.0F));
            float cy = e.y + e.fin(Interp.pow5In) * (100.0F + Mathf.randomSeedRange(e.id + 2, 31.0F));
            float rotation = e.fin() * (130.0F + Mathf.randomSeedRange(e.id, 80));
            Draw.z(110.001F);
            Draw.color(Pal.redLight);
            float rad = 6F + e.fslope();
            Fill.light(cx, cy, 10, 25.0F * (rad + scale - 1.0F), Tmp.c2.set(Pal.engine).a(alpha), Tmp.c1.set(Pal.engine).a(0.0F));
            Draw.alpha(alpha);
            for (int i = 0; i < 4; ++i) {
                Drawf.tri(cx, cy, 6.0F, 40.0F * (rad + scale - 1.0F), (float) i * 90.0F + rotation);
            }
            Draw.color();
            Draw.z(129.0F);
            float rw = (float) region.width * Draw.scl * scale;
            float rh = (float) region.height * Draw.scl * scale;
            Draw.alpha(alpha);
            Draw.rect(region, cx, cy, rw, rh, rotation);
            Tmp.v1.trns(225.0F, e.fin(Interp.pow3In) * 250.0F);
            Draw.z(116.0F);
            Draw.color(0.0F, 0.0F, 0.0F, 0.22F * alpha);
            Draw.rect(region, cx + Tmp.v1.x, cy + Tmp.v1.y, rw, rh, rotation);
            Effect.shake(size * 2, 16f, e.x, e.y);
            Draw.reset();
        });
    }
}
