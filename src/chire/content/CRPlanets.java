package chire.content;

import arc.graphics.Color;
import chire.maps.planet.AsteroidPlanetGenerator;
import chire.maps.planet.BlackHolePlanetGenerator;
import chire.maps.planet.SunPlanetGenerator;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.PlanetGrid;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.world.meta.Env;

public class CRPlanets {
    public static Planet blackhole, Asteroid;
    public static void load(){
        Planet sun = Planets.sun;
        sun.alwaysUnlocked = true;
        sun.hasAtmosphere = false;
        sun.updateLighting = false;
        sun.accessible = true;
        sun.sectors.add(new Sector(sun, PlanetGrid.Ptile.empty));
        sun.defaultEnv = Env.space;
        sun.generator = new SunPlanetGenerator();

        blackhole = new Planet("Black-Hole", null, 1f, 2){{
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

        Asteroid = new Planet("Asteroid-s", Planets.serpulo, 0.3f, 3){{
            generator = new AsteroidPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
//            cloudMeshLoader = () -> new MultiMesh(
//                    new HexSkyMesh(this, 11, 0.15f, 0.13f, 5, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
//                    new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
//            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            startSector = 17;
            orbitRadius = 6;
            orbitTime = 12 * 2;
            allowWaves = true;
            allowWaveSimulation = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            //doesn't play well with configs
            prebuildBase = false;
            hasAtmosphere = true;
            ruleSetter = r -> {
                r.waveTeam = Team.crux;
                r.placeRangeCheck = false;
                r.attributes.clear();
                r.showSpawns = false;
            };
            iconColor = Color.valueOf("daf5ff");
            atmosphereColor = Color.valueOf("ffffff");
            atmosphereRadIn = 0.02f;
            atmosphereRadOut = 0.3f;
            startSector = 15;
            alwaysUnlocked = false;
            accessible =false;
            visible = false;
            landCloudColor = Pal.spore.cpy().a(0.5f);
            hiddenItems.addAll(Items.erekirItems).removeAll(Items.serpuloItems);
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
