const lib = require("lib");
const a = new Planet("月", Planets.serpulo, 0.3, 3.3);
a.meshLoader = prov(() => new MultiMesh(
    new HexMesh(a, 5)
));
a.generator = new AsteroidGenerator();
a.atmosphereColor = Color.valueOf("AAAAAAFF");
a.atmosphereRadIn = 1;
a.atmosphereRadOut = 1;
a.localizedName = "月";
a.tidalLock = true;
a.visible = true;
a.bloom = true;
a.accessible = false;
a.alwaysUnlocked = false;
a.orbitTime = 12 * 2;
a.startSector = 0;
a.orbitRadius = 6;


/*
发生错误:由于代码过于老旧以及游戏的适配原因两个星球无法同时存在，只能添加一个星球，等技术好了再慢慢改吧
修改:成功运行
*/