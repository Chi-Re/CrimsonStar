const lib = require("lib");
const sun2 = new JavaAdapter(Planet, {},
    "恒810", null, 8);//恒星
sun2.bloom = true;
sun2.accessible = false
sun2.visible = true;
sun2.localizedName = "恒810"
sun2.orbitRadius = 200;
sun2.meshLoader = () => new SunMesh(sun2, 5, 6, 3.4, 2.8, 1.3, 0.8, 1.1,
    Color.valueOf("8FFBFFFF"),
    Color.valueOf("5AAAFF"),
    Color.valueOf("4CA3FF"),
    Color.valueOf("488CD6"),
    Color.valueOf("90C6FF"),
    Color.valueOf("B2D7FF")
);
lib.addToResearch(sun2, { parent: SectorPresets.planetaryTerminal.name, });
//代码来源:创世神V7