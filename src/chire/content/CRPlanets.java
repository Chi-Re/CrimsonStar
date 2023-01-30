package chire.content;

import arc.files.Fi;
import arc.graphics.Color;
import arc.math.Rand;
import arc.math.geom.Mat3D;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Tmp;
import chire.maps.planet.*;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.*;
import mindustry.maps.planet.AsteroidGenerator;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Env;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static mindustry.Vars.state;

public class CRPlanets {
    public static Planet blackhole,
            desertplanet, Asteroid,
            CRV;
    public static void load() {
//        Planet sun = Planets.sun;
//        sun.alwaysUnlocked = true;
//        sun.hasAtmosphere = false;
//        sun.updateLighting = false;
//        sun.accessible = true;
//        sun.sectors.add(new Sector(sun, PlanetGrid.Ptile.empty));
//        sun.defaultEnv = Env.space;
//        sun.generator = new SunPlanetGenerator();

        //沙漠星球
        desertplanet = new Planet("desert-planet", Planets.sun, 1f, 2) {{
            generator = new DesertPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 2, 0.15f, 0.14f, 5, Color.valueOf("eba768").a(0.75f), 2, 0.42f, 1f, 0.43f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 1.2f, 0.45f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 0.8f, 0.41f),
                    new HexSkyMesh(this, 3, 0.6f, 0.15f, 5, Color.valueOf("eea293").a(0.75f), 2, 0.42f, 1.5f, 0.5f)
            );
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("ed6542");
            atmosphereColor = Color.valueOf("8F7C3E");
//            defaultEnv = Env.scorching | Env.terrestrial;
            hiddenItems.addAll(Items.erekirItems).removeAll(CRItems.AsteroidPlanetItems);

            startSector = 10;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            tidalLock = false;
            orbitSpacing = 2f;
            totalRadius += 2.6f;
            lightSrcTo = 0.5f;
            lightDstFrom = 0.2f;
            rotateTime = 24 * 100f;
            clearSectorOnLose = true;
            defaultCore = CRBlocks.desertcore;
            iconColor = Color.valueOf("A16C50");
            //hiddenItems.addAll(Items.serpuloItems).removeAll(Items.erekirItems);
            enemyBuildSpeedMultiplier = 0.4f;

            //TODO SHOULD there be lighting?
            updateLighting = true;

            unlockedOnLand.add(CRBlocks.desertcore);
        }};

        Planet datajson;

//        File serpulofile = new File(Vars.modDirectory  + "/settings/planets/serpulo.json");
//        if (serpulofile.exists()){
//            datajson = Planets.serpulo;
//        }
//        File desertplanetfile = new File(Vars.modDirectory  + "/settings/planets/desertplanet.json");
//        if (desertplanetfile.exists()){
//            datajson = Planets.serpulo;
//        }


//        Log.info(Vars.state.rules.sector.planet.parent);

        //TODO 判断星球,之后将更改为更方便的
        Planet parent;
        Fi planetsfile = new Fi(Vars.modDirectory + "/settings/planets");
        if (!planetsfile.exists()) {
            planetsfile.mkdirs();
        }

        File serpulofile = new File(Vars.modDirectory + "/settings/planets/serpulo.stt");
        if (serpulofile.exists()) {
            parent = Planets.serpulo;
        } else {
            parent = desertplanet;
        }

        //空间站spacestation(放弃改为小行星)
        Asteroid = new Planet("asteroid", parent, 1f) {{
            generator = new AsteroidPlanetGenerator();

            hiddenItems.addAll(Items.erekirItems).removeAll(CRItems.AsteroidPlanetItems);

            alwaysUnlocked = true;
            tidalLock = false;
            clearSectorOnLose = true;
            hasAtmosphere = false;
            updateLighting = false;
            accessible = true;
            orbitRadius = 6;
            sectors.add(new Sector(this, PlanetGrid.Ptile.empty));
            defaultEnv = Env.terrestrial;
            meshLoader = () -> {
                iconColor = Blocks.snow.mapColor;
                Color tinted = Blocks.iceSnow.mapColor.cpy().a(1f - Blocks.iceSnow.mapColor.a);
                Seq<GenericMesh> meshes = new Seq<>();
                Color color = Blocks.stoneWall.mapColor;
                Rand rand = new Rand(id + 2);
                meshes.add(new NoiseMesh(
                        this, 0, 2, 0.1f, 2, 0.55f, 0.45f, 2f,
                        color, tinted, 3, 0.6f, 0.38f, 0.5f
                ));
                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};


        //星环
        // by:你在干什么
        //由我进行移植,但不完善,
        CRV = new Planet("星环", Planets.sun, 1f) {{
            localizedName = "星环";
            orbitRadius = 0;
            hasAtmosphere = false;
            camRadius = 1;
            minZoom = 0.01f;
            alwaysUnlocked = false;
            sectors.add(new Sector(this, new PlanetGrid.Ptile(0, 0)));
            generator = new AsteroidGenerator();
            meshLoader = () -> {
                iconColor = Blocks.iceWall.mapColor;
                Color tinted = Blocks.iceWall.mapColor;
                Seq<GenericMesh> meshes = new Seq<>();
                Color color = Blocks.stoneWall.mapColor;
                Rand rand = new Rand(id + 2);
                for (int j = 0; j < 900; j++) {
                    Vec2 v2 = new Vec2();
                    v2.setToRandomDirection().setLength(rand.random(0.7f, 1.3f));
                    Vec2 v22 = new Vec2(v2.y, rand.random(-0.1f, 0.1f));
                    v22.rotate(60);
                    meshes.add(new MatMesh(
                            new NoiseMesh(this, j + 1, 1, 0.022f + rand.random(0.039f) * 1, 2, 0.6f, 0.38f, 20, color, tinted, 3, 0.6f, 0.38f, 0.5f),
                            new Mat3D().setToTranslation(new Vec3(v2.x, v22.x, v22.y).scl(40))
                    ));
                }
                return new MultiMesh(meshes.toArray(GenericMesh.class));
            };
        }};
        //卫星(no)


        blackhole = new Planet("Black-Hole", null, 1f, 2) {{
            generator = new BlackHolePlanetGenerator();
            meshLoader = () -> new HexMesh(this, 5);
            alwaysUnlocked = true;
            landCloudColor = Color.valueOf("000000");
            atmosphereColor = Color.valueOf("000000");
            startSector = 17;
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            tidalLock = false;
            camRadius = 10;
            orbitTime = 180 * 6;
            rotateTime = 24 * 3;
            orbitSpacing = 4f;
            lightSrcTo = 0.5f;
            lightDstFrom = 0.2f;
            clearSectorOnLose = true;
            visible = true;
            hiddenItems.addAll(Items.erekirOnlyItems).removeAll(Items.serpuloItems);
            updateLighting = false;

            ruleSetter = r -> {
                r.waveTeam = Team.blue;
                r.placeRangeCheck = false;
                r.attributes.clear();
                r.showSpawns = false;
            };

//            unlockedOnLand.add(DTBlocks.corePedestal);
        }};


//        chire = new Planet("chire", Planets.serpulo, 2f){{
//            bloom = true;
//            accessible = true;
//            hasAtmosphere = false;
//            updateLighting = false;
//            orbitRadius = 8;
//            alwaysUnlocked = true;
//            meshLoader = () -> new SunMesh(
//                    this, 4,
//                    5, 0.3, 1.7, 1.2, 1,
//                    1.1f,
//                    Color.valueOf("100C32"),
//                    Color.valueOf("100C32"),
//                    Color.valueOf("100C32"),
//                    Color.valueOf("100C32"),
//                    Color.valueOf("100C32"),
//                    Color.valueOf("100C32")
//            );
//        }};
    }
}
