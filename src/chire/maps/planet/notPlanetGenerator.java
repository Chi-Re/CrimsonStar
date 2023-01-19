package chire.maps.planet;

import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.ObjectMap;
import arc.util.Tmp;
import arc.util.noise.Simplex;
import mindustry.content.Blocks;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.world.Block;

public class notPlanetGenerator extends SerpuloPlanetGenerator {
    public float heightScl = 0.9f, octaves = 8, persistence = 0.7f;

    float scl = 5f;
    Block[][] arr = {
            {Blocks.carbonStone}
    };
    ObjectMap<Block, Block> tars = ObjectMap.of(
            Blocks.sporeMoss, Blocks.shale,
            Blocks.moss, Blocks.shale
    );

    @Override
    public Color getColor(Vec3 position){
        Block block = getBlock(position);

        //more obvious color
        if(block == Blocks.crystallineStone) block = Blocks.crystalFloor;
        //TODO this might be too green
//        if(block == Blocks.beryllicStone) block = Blocks.arkyicStone;

        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }
    float rawHeight(Vec3 position){
        return Simplex.noise3d(seed, octaves, persistence, 1f/heightScl, 10f + position.x, 10f + position.y, 10f + position.z);
    }
    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f/3f, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        float tar = Simplex.noise3d(seed, 4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

        Block res = arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
        if(tar > 0.5f){
            return tars.get(res, res);
        }else{
            return res;
        }
    }
}