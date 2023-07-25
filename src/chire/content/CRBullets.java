package chire.content;

import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.MissileBulletType;

public class CRBullets {
    public static BulletType Explode;

    public static void load() {
        Explode = new MissileBulletType(3f, 550) {{
            width = 8f * 1.5f;
            lifetime = 40f;
            height = 8f * 2;
            shrinkY = 0f;
            splashDamageRadius = 8 * 8f;
            splashDamage = 444;
            hitEffect = Fx.blastExplosion;
            despawnEffect = Fx.blastExplosion;
            status = StatusEffects.blasted;
            statusDuration = 60f;
            fragBullets = 4;
            fragBullet = new MissileBulletType(5, 315) {{
                width = 8f * 1.5f;
                lifetime = 40f;
                height = 8f * 2;
                shrinkY = 0f;
                splashDamageRadius = 8 * 8f;
                splashDamage = 250;
                hitEffect = Fx.blastExplosion;
                despawnEffect = Fx.blastExplosion;
                status = StatusEffects.blasted;
                statusDuration = 60f;
            }};
        }};
    }
}
