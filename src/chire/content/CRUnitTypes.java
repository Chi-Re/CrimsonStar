package chire.content;

import mindustry.ai.types.BuilderAI;
import mindustry.content.Fx;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.gen.Sounds;
import mindustry.gen.UnitEntity;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.PowerAmmoType;

public class CRUnitTypes {
    public static UnitType separate;

    public static void load(){
        separate = new UnitType("separate"){{
            constructor = UnitEntity::create;
            ammoType = new PowerAmmoType(900);
            aiController = BuilderAI::new;
            isEnemy = false;

            lowAltitude = true;
            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 0.5f;
            drag = 0.05f;
            speed = 3f;
            rotateSpeed = 15f;
            accel = 0.1f;
            itemCapacity = 30;
            health = 150f;
            engineOffset = 6f;
            hitSize = 8f;
            alwaysUnlocked = true;
            weapons.add(new Weapon("disintegration-laser-bolt-mount"){{
                reload = 17f;
                x = 2f;
                y = 0f;
                top = false;
                shootSound = Sounds.lasershoot;
                bullet = new LaserBoltBulletType(2.5f, 10){{
                    collidesTeam = true;
                    healPercent = 2;
                    lifetime = 60f;
                    smokeEffect = Fx.shootSmallSmoke;
                    buildingDamageMultiplier = 0.01f;
                    healColor = Pal.bulletYellow;
                }};
            }});
        }};
    }
}
