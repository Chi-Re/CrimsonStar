const lib = require("lib");
const free = new Planet("斯图尔特", Planets.sun, 1, 3.3);
free.meshLoader = prov(() => new MultiMesh(
    new HexMesh(free, 5)
));
free.cloudMeshLoader = prov(() => new MultiMesh(
    new HexSkyMesh(free, 2, 0.15, 0.14, 5, Color.valueOf("ff9638de"), 2, 0.42, 1, 0.43),
    new HexSkyMesh(free, 3, 0.6, 0.15, 5, Color.valueOf("f4ee8e88"), 2, 0.42, 1.2, 0.45)
));//颜色已更改
free.generator = new ErekirPlanetGenerator();
free.atmosphereColor = free.lightColor = Color.valueOf("F75000BE");
free.atmosphereRadIn = 0.1;
free.atmosphereRadOut = 0.5;
free.localizedName = "斯图尔特";
free.visible = true;
free.bloom = false;
free.accessible = true;
free.alwaysUnlocked = true;
free.clearSectorOnLose = true;
free.startSector = 233;
free.orbitRadius = 7;
free.orbitTime = 180 * 6;
free.rotateTime = 24 * 3;
//free.defaultCore = 炽工业-C型号核心;
free.hiddenItems.addAll(Items.serpuloItems).removeAll(Items.erekirItems);
free.defaultEnv = Env.scorching | Env.terrestrial;
//free.techTree = nodeRoot("斯图尔特", C型号核心, true, () => {})

const maps = new SectorPreset("233基地", free, 233);
maps.alwaysUnlocked = true;
maps.captureWave = 1;
maps.difficulty = 5;
maps.localizedName = "233基地";
exports.maps = maps;
lib.addToResearch(maps, {
    parent: 'planetaryTerminal',
    objectives: Seq.with(
        new Objectives.SectorComplete(SectorPresets.groundZero))
});


/*
故事背景(未完成):
距离恒星非常近的熔岩行星，上面蕴含大量矿物以及太阳导致的高能粒子存在，十分危险，只能在南北两极建设长期根据地，但敌人非常多，请在有准备的情况下前往

科技:
埃里克尔德附庸科技，同属于在高温高压下可使用的设备，但结构截然不同，所需要的矿物更为珍贵。

地图(暂时名称):
233基地
极端之地
中转站
热流涌动
西北矿区
粒子风暴场
伽马辐射
陨石区
禁区<--163协议-->
崩坏
炽热区
融化
寸步难行
辐射区
大气漏洞

建筑/物品
   - 中央环境稳定立场(小/大)(不可摧毁?)
   - 大量遗迹(建筑/单位)
   
气候
   -太阳风暴
   -等离子辐射
还没想好呢，慢慢做吧
*/