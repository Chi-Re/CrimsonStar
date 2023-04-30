package chire.content;

import arc.Core;
import arc.graphics.Color;
import arc.struct.Seq;
import com.alibaba.fastjson2.JSONObject;
import mindustry.Vars;
import mindustry.content.Planets;
import mindustry.gen.Icon;
import mindustry.graphics.g3d.HexMesh;
import mindustry.maps.planet.TantrosPlanetGenerator;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.world.meta.Env;

import java.util.*;

import static chire.ui.PopDialogs.showToast;
import static chire.util.CoreCache.*;

public class CRPlanets {
    public static Planet tantross;
    /**每次游戏启动时设置更改(例如:轨道距离，是否摧毁)*/
    public static Planet[] stablePlanets = {Planets.sun, Planets.tantros, Planets.gier, Planets.notva, Planets.verilus};
    public static Seq<Planet> allPlanet = Vars.content.planets();
    public static void load(){
        tantross = new Planet("tantros", Planets.serpulo, 1f, 2){{
            generator = new TantrosPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 4);
            atmosphereColor = Color.valueOf("3db899");
            iconColor = Color.valueOf("597be3");
            startSector = 10;
            orbitSpacing = 2f;
            alwaysUnlocked = true;
            atmosphereRadIn = -0.01f;
            atmosphereRadOut = 0.3f;
            defaultEnv = Env.underwater | Env.terrestrial;
            ruleSetter = r -> {

            };
        }};



        putAny();
    }

    public static void putAny() {
        String planetdata;
        for (Planet planet : allPlanet) {
            planetdata = getString(planet.name);
            if (Objects.equals(planetdata, "")) {
//                planetdata =
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("name", "chire");
                new JSONObject(map);
            }
        }
    }

    /**摧毁星球*/
    public static void destroy(Planet planet, Runnable runnable) {
        /*防止有个吊毛把家炸了*/
        if (planet == Planets.serpulo && planet == Planets.erekir) {
            if (runnable != null) runnable.run();
        } else {
            for (Sector sector : planet.sectors) {
                //Events.fire(new EventType.SectorLoseEvent(sector));
                sector.info.items.clear();
                sector.info.damage = 1f;
                sector.info.hasCore = false;
                sector.info.production.clear();
            }
            planet.alwaysUnlocked = false;
            planet.accessible = false;
            planet.visible = false;
            showToast(Icon.warning, Core.bundle.format("planet.lost", planet.name));
        }
    }
    public static void destroy(Planet planet) {
        destroy(planet, null);
    }
    public static void putPlanetaryData(Planet planet, boolean alwaysUnlocked, boolean accessible, boolean visible){
        String data = jsonB(
                jsonA("alwaysUnlocked", alwaysUnlocked),
                jsonA("accessible", accessible),
                jsonA("visible", visible, true)
        );
//        Jval.JsonArray data = new Jval.JsonArray();
//        data.add(Jval.valueOf("name"), Jval.valueOf(true));
        put(planet.name, data);
    }
    public static void putPlanetaryData(Planet planet){
        putPlanetaryData(planet, planet.alwaysUnlocked, planet.accessible, planet.visible);
    }





    /**判断是否满足未被摧毁的条件*/
    public static boolean notDestroyPlanet(Planet planet) {
        //||是双方满足一个，&&是全部
        //return (planet.accessible && planet.visible);
        return planet.visible;
    }
    public static boolean onDestroyPlanet(Planet planet) {
        return !notDestroyPlanet(planet);
    }
    public static List<Planet> notDestroyPlanets(Seq<Planet> planets) {
        List<Planet> planetList = new ArrayList<>();
        for (Planet planet : planets) {
            if (notDestroyPlanet(planet)) planetList.add(planet);
        }
        return planetList;
    }
    /**获取你已经毁灭的星球*/
    public static List<Planet> noDestroyPlanets(Seq<Planet> planets) {
        List<Planet> planetList = new ArrayList<>();
        for (Planet planet : planets) {
            if (onDestroyPlanet(planet)) planetList.add(planet);
        }
        return planetList;
    }
    public static boolean canAccessiblePlanet(Planet planet) {
        return (planet.accessible && planet.visible);
    }
    public static boolean notAccessiblePlanet(Planet planet) {
        return canAccessiblePlanet(planet);
    }
    /**用来查哪些星球可以访问，防止星球过少导致UI出错*/
    public static List<Planet> canAccessiblePlanets(Seq<Planet> planets) {
        List<Planet> planetList = new ArrayList<>();
        for (Planet planet : planets) {
            if (canAccessiblePlanet(planet)) planetList.add(planet);
        }
        return planetList;
    }
    public static List<Planet> notAccessiblePlanets(Seq<Planet> planets) {
        List<Planet> planetList = new ArrayList<>();
        for (Planet planet : planets) {
            if (notAccessiblePlanet(planet)) planetList.add(planet);
        }
        return planetList;
    }
}
