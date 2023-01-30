package chire.content;

import arc.graphics.Color;
import arc.math.Interp;
import chire.world.CRColer;
import chire.world.blocks.campaign.NewAccelerator;
import chire.world.blocks.campaign.PlanetLaunchPad;
import chire.world.blocks.campaign.SunLaunchPad;
import chire.world.blocks.defense.turrets.LastResortTurret;
import chire.world.blocks.distribution.CoiledDuct;
import chire.world.blocks.production.DiggingWall;
import chire.world.blocks.production.MineralCollectors;
import chire.world.blocks.special.NewtwoWhit;
import chire.world.blocks.storage.AsteroidPlanetCoreBlock;
import chire.world.blocks.storage.DesertCoreBlock;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.PointBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.blocks.environment.EmptyFloor;
import mindustry.world.blocks.sandbox.ItemSource;
import mindustry.world.blocks.sandbox.PowerSource;
import mindustry.world.meta.BuildVisibility;

import static mindustry.type.ItemStack.with;


public class CRBlocks {
    public static Block CRlaunchPad, CRinterplanetaryAccelerator,
            lastresort, lightSpace, CRpowerSource, CRitemSource,

    //斯坦沐星球方块
    coiledduct, desertcore, CRPlaunchPad, asteroidcore, CRBseparator, mineralCollectors
    //环境

        ;
    public CRBlocks() {
    }
    public static void loadEnv() {

        lightSpace = new EmptyFloor("light-space");

        asteroidcore = new AsteroidPlanetCoreBlock("asteroid-core"){{
            requirements(Category.effect, with(Items.copper, 2));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = UnitTypes.alpha;
            health = 1100;
            itemCapacity = 4000;
            size = 3;

            powerOutput = 2000f;

            unitCapModifier = 8;
        }};

        desertcore = new DesertCoreBlock("desert-core"){{
            requirements(Category.effect, with(Items.copper, 2));
            alwaysUnlocked = true;

            isFirstTier = true;
            unitType = UnitTypes.alpha;
            health = 1100;
            itemCapacity = 4000;
            size = 4;

            powerOutput = 2000f;

            unitCapModifier = 8;
        }};
        mineralCollectors = new MineralCollectors("mineral-collectors"){{
            requirements(Category.production, with(Items.copper, 1));
            results = with(
                    Items.copper, 2,
                    Items.lead, 2,
                    Items.graphite, 3,
                    Items.sand, 10,
                    Items.coal, 3
            );
            hasPower = true;
            craftTime = 35f;
            size = 3;

            powerOutput = 200f;
        }};
        //选择行星方块
        //TODO 动画制作未完成
        CRPlaunchPad = new PlanetLaunchPad("CRP-launch-pad"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly,with(Items.copper, 3));
            size = 3;
            itemCapacity = 100;
            launchTime = 60f * 20;
            hasPower = false;
//            consumePower(4f);
        }};

        CRBseparator = new DiggingWall("CR-B-separator"){{
            requirements(Category.production, with(Items.copper, 1));
            blocks = new Block[]{Blocks.stoneWall, Blocks.iceWall};
            blocksTime = new float[]{0.1f, 5};
            results = with(
                    Items.copper, 4, Items.lead, 3, Items.graphite, 1, Items.titanium, 1,
                    CRItems.iceresidue, 3, Items.sand, 5
            );
            hasPower = true;
            hasLiquids = false;
            craftTime = 35f;
            size = 2;

            consumePower(1.1f);
        }};


        CRlaunchPad = new SunLaunchPad("CR-launch-pad"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly,with(Items.copper, 3));
            size = 3;
            itemCapacity = 100;
            launchTime = 60f * 20;
            hasPower = false;
//            consumePower(4f);
        }};
        //TODO 模组测试作弊
        CRpowerSource = new PowerSource("CR-power-source"){{
            requirements(Category.power, BuildVisibility.sandboxOnly,with());
            powerProduction = 1000000f / 60f;
            alwaysUnlocked = true;
        }};
        CRitemSource = new ItemSource("CR-item-source"){{
            requirements(Category.distribution,BuildVisibility.sandboxOnly, with());
            alwaysUnlocked = true;
        }};

        CRinterplanetaryAccelerator = new NewAccelerator("new-interplanetary-accelerator"){{
            requirements(Category.effect, BuildVisibility.sandboxOnly,with(Items.copper, 16));
            researchCostMultiplier = 0.1f;
            size = 7;
            hasPower = true;
            consumePower(10f);
            buildCostMultiplier = 0.5f;
            scaledHealth = 80;
            consumeItems(with(Items.copper, 4));
        }};

        coiledduct = new CoiledDuct("coiled-duct"){{
            requirements(Category.distribution, with(Items.copper, 1));
            health = 90;
            speed = 4f;
            buildCostMultiplier = 2f;
            hasPower = true;
            consumesPower = true;
            conductivePower = true;
            researchCost = with(Items.copper, 5);
        }};
//            displayedSpeed = 4.2f;

//        asd = new BuildPayload(chireduo, Team.derelict){};

        //尝试复刻而且加强<最终手段> json源作者:Ra
        lastresort = new LastResortTurret("Last-Resort") {{
            description = "[yellow]呼叫一次[red]无视敌我的轨道轰炸[yellow]毁灭附近的区域。[white]检测范围小，需要机载投放。\n[yellow]需要5秒开机准备时间.";
            health = 830;
            size = 2;
            reload = 280;
            canOverdrive = false;
            range = 160;
            rebuildable = false;
            inaccuracy = 0;
            rotateSpeed = 99;
            requirements(Category.effect,BuildVisibility.sandboxOnly, with(Items.lead, 50, Items.silicon, 30));
            shootType = new BasicBulletType(0, 112) {{
                killShooter = true;
                hittable = false;
                absorbable = false;
                //hitSound = ;//TODO 可能不可用
                ammoMultiplier = 1;
                speed = 0;
                lifetime = 1;
                hitShake = 20;
                damage = 112;
                splashDamageRadius = 500;
                splashDamage = 5800;
                statusDuration = 600;
            }};
            destroyEffect = new MultiEffect(
                    new WaveEffect() {{
                        lifetime = 120;
                        sizeFrom = 40;
                        sizeTo = 500;
                        strokeFrom = 4;
                        strokeTo = 0;
                        colorFrom = CRColer.SaturatedRed;
                        colorTo = CRColer.SaturatedRed;
                    }},
                    new WaveEffect() {{
                        lifetime = 15;
                        sizeFrom = 0;
                        sizeTo = 500;
                        strokeFrom = 3;
                        strokeTo = 0;
                        colorFrom = Color.white.cpy();
                        colorTo = Color.white.cpy();
                    }}
            );
            destroyBullet = new BasicBulletType() {{
                width = 0;
                height = 0;
                speed = 0;
                lifetime = 780;
                collides = false;
                hittable = false;
                absorbable = false;
                hitShake = 20;
                //hitSound = CRSounds.DBZ1;
                hitEffect = new MultiEffect() {{
                    lifetime = 120;
                    new ParticleEffect() {{
                        particles = 50;
                        sizeFrom = 20;
                        sizeTo = 0;
                        length = 500;
                        baseLength = 8;
                        lifetime = 110;
                        interp = Interp.fastSlow;
                        colorFrom = CRColer.SaturatedRed;
                        //colorFrom = new Color(0xFF5B5BFF);
                        colorTo = CRColer.SaturatedRed;
                    }};
                    new WaveEffect() {{
                        sizeFrom = 8;
                        sizeTo = 500;
                        lifetime = 15;
                        strokeFrom = 16;
                        strokeTo = 0;
                        colorFrom = CRColer.SaturatedRed;
                        colorTo = CRColer.SaturatedRed;
                    }};
                    new WaveEffect() {{
                        sizeFrom = 8;
                        sizeTo = 500;
                        lifetime = 200;
                        strokeFrom = 48;
                        strokeTo = 0;
                        colorFrom = CRColer.SaturatedRed;
                        colorTo = CRColer.SaturatedRed;
                    }};
                }};
                splashDamageRadius = 500;
                splashDamage = 3800;
                fragLifeMin = 1;
                fragBullets = 325;
                fragBullet = new BasicBulletType() {{
                    width = 0;
                    height = 0;
                    speed = 5.6f;
                    lifetime = 95;
                    collides = false;
                    hittable = false;
                    absorbable = false;
                    splashDamageRadius = 88;
                    splashDamage = 666;
                    hitShake = 11;
                    hitSound = Sounds.explosionbig;
                    hitEffect = new MultiEffect(
                            new ParticleEffect() {{
                                interp= Interp.fastSlow;
                                particles=13;
                                sizeFrom=15;
                                sizeTo=0;
                                length=55;
                                baseLength=39;
                                lifetime=95;
                                colorFrom= CRColer.SaturatedRed;
                                colorTo= Color.white.cpy();
                                cone= 360;
                            }},
                    new WaveEffect() {{
                        sizeFrom=6;
                        sizeTo=88;
                        lifetime=10;
                        strokeFrom = 6;
                        strokeTo = 0;
                        colorFrom= CRColer.SaturatedRed;
                        colorTo= CRColer.SaturatedRed;
                    }}
                    );
                }};
                bulletInterval = 1;
                intervalBullets = 10;
                intervalRandomSpread = 360;
                intervalSpread = 0;
                intervalAngle = 0;
                intervalBullet = new PointBulletType() {{
                    lifetime = 1;
                    speed = 1;
                    fragBullets = 1;
                    fragLifeMin = 0.3f;
                    fragBullet = new BasicBulletType() {{
                        collides = false;
                        hittable = false;
                        absorbable = false;
                        width = 0;
                        height = 0;
                        speed = 50;
                        lifetime = 10;
                        damage = 100;
                        hitSound = Sounds.plasmadrop;
                        hitEffect = new WaveEffect() {{
                            lifetime = 120;
                            sizeFrom = 45;
                            sizeTo = 0;
                            strokeFrom = 0;
                            strokeTo = 4;
                            colorFrom = CRColer.SaturatedRed;
                            colorTo = CRColer.SaturatedRed2;
                        }};
                        fragLifeMin = 1;
                        fragLifeMax = 1;
                        fragBullets = 1;
                        fragBullet = new BulletType() {{
                            hitEffect = new MultiEffect(
                                new ParticleEffect() {{
                                    particles = 13;
                                    interp = Interp.fastSlow;
                                    sizeFrom = 15;
                                    sizeTo = 0;
                                    length = 55;
                                    baseLength = 39;
                                    lifetime = 35;
                                    colorFrom = CRColer.SaturatedRed;
                                    colorTo = Color.white.cpy();
                                    cone = 360;
                                }},
                                new ParticleEffect() {{
                                    particles = 1;
                                    interp = Interp.fastSlow;
                                    sizeFrom = 15;
                                    sizeTo = 0;
                                    length = 0;
                                    baseLength = 0;
                                    lifetime = 15;
                                    //region = "blazindustry-star";
                                    colorFrom = CRColer.SaturatedRed;
                                    colorTo = CRColer.SaturatedRed;
                                    cone = 360;
                                }},
                                new WaveEffect() {{
                                    lifetime = 10;
                                    sizeFrom = 6;
                                    sizeTo = 88;
                                    strokeFrom = 6;
                                    strokeTo = 0;
                                    colorFrom = CRColer.SaturatedRed;
                                    colorTo = CRColer.SaturatedRed;
                                }}
                            );
                            }};
                        }};
                        hitShake = 11;
                        hitSound = Sounds.largeExplosion;
                        width = 0;
                        height = 0;
                        collides = false;
                        hittable = false;
                        absorbable = false;
                        splashDamageRadius = 88;
                        splashDamage = 235;
                        lifetime = 120;
                        speed = 0;
                    }};
                }};
            }};
    }
}

