package chire.world.blocks.production;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Time;
import arc.util.Tmp;
import chire.world.blocks.laser.LaserBlock;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.production.Separator;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class DiggingWall extends Separator{
    public double range = 3;
    public Block[] blocks;

    public DiggingWall(String name) {
        super(name);
        rotate = true;
        blocks = new Block[]{Blocks.stoneWall, Blocks.duo, Blocks.coreShard};
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        for (Block block : blocks) {
            for (int i = 0; i < size; i++) {
                nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
                for (int j = 0; j < range; j++) {
                    Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation) * j, Tmp.p1.y + Geometry.d4y(rotation) * j);
                    if (other != null && other.solid()) {
//                    Item drop = other.wallDrop();
                        if (other.block() == block) {
                            return true;
                        }
                        break;
                    }
                }
            }
        }

        return false;
    }

    public class DiggingWallBuild extends SeparatorBuild {
        public float lx = x, ly = y, lsx = x, lsy = y;
        @Override
        public void draw(){
            drawer.draw(this);;

            for (Block block : blocks) {
                for (int i = 0; i < size; i++) {
                    nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
                    for (int j = 0; j < range; j++) {
                        Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation) * j, Tmp.p1.y + Geometry.d4y(rotation) * j);
                        if (other != null && other.solid()) {
                            if (other.block() == block) {
                                float width = (0.65f + Mathf.absin(Time.time + i*5 + (id % 9)*9, 3f, 0.07f)) * warmup;
                                if (rotation == 0){
                                    lx = x + 2;
                                    ly = y;
                                    lsx = other.worldx();
                                    lsy = y;
                                }
                                else if (rotation == 2){
                                    lx = x - 2;
                                    ly = y;
                                    lsx = other.worldx();
                                    lsy = y;
                                }
                                else if (rotation == 1){
                                    lx = x;
                                    ly = y + 2;
                                    lsx = x;
                                    lsy = other.worldy();
                                }
                                else if (rotation == 3){
                                    lx = x;
                                    ly = y - 2;
                                    lsx = x;
                                    lsy = other.worldy();
                                }
//                                switch (rotation) {
//                                    case 0:
//                                        lx = x + 2;
//                                        ly = y;
//                                        lsx = other.worldx();
//                                        lsy = y;
//                                    case 1:
//                                        lx = x;
//                                        ly = y + 2;
//                                        lsx = x;
//                                        lsy = other.worldy();
//                                    case 2:
//                                        lx = x - 2;
//                                        ly = y;
//                                        lsx = other.worldx();
//                                        lsy = y;
//                                    case 3:
//                                        lx = x;
//                                        ly = y - 2;
//                                        lsx = x;
//                                        lsy = other.worldy();
//                                }
                                Draw.z(Layer.power - 1);
                                Draw.mixcol(Color.white, Mathf.absin(Time.time + i*5 + id*9, 3f, 0.07f));
                                Drawf.laser(
                                        Core.atlas.find("blazindustry-point-laser"),
                                        Core.atlas.find("blazindustry-point-laser-end"),
                                        lsx, lsy,
                                        lx, ly,
                                        width);
                                Vars.ui.showLabel(String.valueOf(rotation), 0.015f, this.x, this.y);
                            }
                            break;
                        }
                    }
                }
            }
        }
    }
}

//package chire.world.blocks.production;
//
//        import arc.graphics.Color;
//        import arc.graphics.g2d.Draw;
//        import arc.graphics.g2d.Lines;
//        import arc.graphics.g2d.TextureRegion;
//        import arc.math.Angles;
//        import arc.math.Mathf;
//        import arc.math.Rand;
//        import arc.math.geom.Geometry;
//        import arc.math.geom.Point2;
//        import arc.util.Nullable;
//        import arc.util.Time;
//        import arc.util.Tmp;
//        import mindustry.content.Blocks;
//        import mindustry.game.Team;
//        import mindustry.graphics.Drawf;
//        import mindustry.graphics.Layer;
//        import mindustry.type.Item;
//        import mindustry.world.Tile;
//        import mindustry.world.blocks.production.Separator;
//        import mindustry.world.draw.DrawBlock;
//
//        import static arc.Core.atlas;
//        import static mindustry.Vars.tilesize;
//        import static mindustry.Vars.world;
//
//public class DiggingWall extends Separator{
//    protected Rand rand = new Rand();
//    public TextureRegion laser = atlas.find("blazindustry-drill-laser");
//    public TextureRegion laserEnd = atlas.find("blazindustry-drill-laser-end");
//    public TextureRegion laserCenter = atlas.find("blazindustry-drill-laser-center");
//    public TextureRegion laserBoost = atlas.find("blazindustry-drill-laser-boost");
//    public TextureRegion laserEndBoost = atlas.find("blazindustry-drill-laser-boost-end");
//    public TextureRegion laserCenterBoost = atlas.find("blazindustry-drill-laser-boost-center");
//
//    public float drillTime = 200f;
//    public int tier = 1;
//    public float laserWidth = 0.65f;
//    public float optionalBoostIntensity = 2.5f;
//
//    public Color sparkColor = Color.valueOf("fd9e81"), glowColor = Color.white;
//    public float glowIntensity = 0.2f, pulseIntensity = 0.07f;
//    public float glowScl = 3f;
//    public int sparks = 7;
//    public float sparkRange = 10f, sparkLife = 27f, sparkRecurrence = 4f, sparkSpread = 45f, sparkSize = 3.5f;
//
//    public Color boostHeatColor = Color.sky.cpy().mul(0.87f);
//    public Color heatColor = new Color(1f, 0.35f, 0.35f, 0.9f);
//    public float heatPulse = 0.3f, heatPulseScl = 7f;
//
//    public int range = 5;
//    public DiggingWall(String name) {
//        super(name);
//        rotate = true;
//    }
//
//    @Override
//    public boolean canPlaceOn(Tile tile, Team team, int rotation){
//        for(int i = 0; i < size; i++){
//            nearbySide(tile.x, tile.y, rotation, i, Tmp.p1);
//            for(int j = 0; j < range; j++){
//                Tile other = world.tile(Tmp.p1.x + Geometry.d4x(rotation)*j, Tmp.p1.y + Geometry.d4y(rotation)*j);
//                if(other != null && other.solid()){
////                    Item drop = other.wallDrop();
//                    if (other.block() == Blocks.stoneWall) {
//                        return true;
//                    }
//                    break;
//                }
//            }
//        }
//
//        return false;
//    }
//
//
//
//    public class DiggingWallBuild extends SeparatorBuild {
//        public Tile[] facing = new Tile[size];
//        public Point2[] lasers = new Point2[size];
//        public @Nullable Item lastItem;
//
//        public float time;
//        public float warmup, boostWarmup;
//        public float lastDrillSpeed;
//
//        @Override
//        public void updateTile(){
//            super.updateTile();
//            if(lasers[0] == null) updateLasers();
//            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 60f);
//            lastItem = null;
//            boolean multiple = false;
//            int dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation), facingAmount = 0;
//            for(int p = 0; p < size; p++){
//                Point2 l = lasers[p];
//                Tile dest = null;
//                for(int i = 0; i < range; i++){
//                    int rx = l.x + dx*i, ry = l.y + dy*i;
//                    Tile other = world.tile(rx, ry);
//                    if(other != null){
//                        if(other.solid()){
////                            Item drop = other.wallDrop();
////                            if(drop != null && drop.hardness <= tier){
//                            facingAmount ++;
////                                if(lastItem != drop && lastItem != null){
////                                    multiple = true;
////                                }
////                                lastItem = drop;
//                            dest = other;
////                            }
////                            break;
//                        }
//                    }
//                }
//
//                facing[p] = dest;
//            }
//        }
//
//        @Override
//        public void draw(){
//            Draw.rect(block.region, x, y);
//            if(isPayload()) return;
//            var dir = Geometry.d4(rotation);
//            int ddx = Geometry.d4x(rotation + 1), ddy = Geometry.d4y(rotation + 1);
//            for(int i = 0; i < size; i++){
//                Tile face = facing[i];
//                if(face != null){
//                    Point2 p = lasers[i];
//                    float lx = face.worldx() - (dir.x/2f)*tilesize, ly = face.worldy() - (dir.y/2f)*tilesize;
//                    float width = (laserWidth + Mathf.absin(Time.time + i*5 + (id % 9)*9, glowScl, pulseIntensity)) * warmup;
//                    Draw.z(Layer.power - 1);
//                    Draw.mixcol(glowColor, Mathf.absin(Time.time + i*5 + id*9, glowScl, glowIntensity));
//                    if(Math.abs(p.x - face.x) + Math.abs(p.y - face.y) == 0){
//                        Draw.scl(width);
//
//                        if(boostWarmup < 0.99f){
//                            Draw.alpha(1f - boostWarmup);
//                            Draw.rect(laserCenter, lx, ly);
//                        }
//
//                        if(boostWarmup > 0.01f){
//                            Draw.alpha(boostWarmup);
//                            Draw.rect(laserCenterBoost, lx, ly);
//                        }
//
//                        Draw.scl();
//                    }else{
//                        float lsx = (p.x - dir.x/2f) * tilesize, lsy = (p.y - dir.y/2f) * tilesize;
//
//                        if(boostWarmup < 0.99f){
//                            Draw.alpha(1f - boostWarmup);
//                            Drawf.laser(laser, laserEnd, lsx, lsy, lx, ly, width);
//                        }
//
//                        if(boostWarmup > 0.001f){
//                            Draw.alpha(boostWarmup);
//                            Drawf.laser(laserBoost, laserEndBoost, lsx, lsy, lx, ly, width);
//                        }
//                    }
//                    Draw.color();
//                    Draw.mixcol();
//
//                    Draw.z(Layer.effect);
//                    Lines.stroke(warmup);
//                    Color col = Color.valueOf("FFB71A");
//                    Color spark = Tmp.c3.set(sparkColor).lerp(boostHeatColor, boostWarmup);
//                    for(int j = 0; j < sparks; j++){
//                        float fin = (Time.time / sparkLife + rand.random(sparkRecurrence + 1f)) % sparkRecurrence;
//                        float or = rand.range(2f);
//                        Tmp.v1.set(sparkRange * fin, 0).rotate(rotdeg() + rand.range(sparkSpread));
//
//                        Draw.color(spark, col, fin);
//                        float px = Tmp.v1.x, py = Tmp.v1.y;
//                        if(fin <= 1f) Lines.lineAngle(lx + px + or * ddx, ly + py + or * ddy, Angles.angle(px, py), Mathf.slope(fin) * sparkSize);
//                    }
//                    Draw.reset();
//                }
//            }
//
//            Draw.blend();
//            Draw.reset();
//        }
//
//        @Override
//        public void onProximityUpdate(){
//            //when rotated.
//            updateLasers();
//        }
//
//        void updateLasers(){
//            for(int i = 0; i < size; i++){
//                if(lasers[i] == null) lasers[i] = new Point2();
//                nearbySide(tileX(), tileY(), rotation, i, lasers[i]);
//            }
//        }
////        @Override
////        public void updateTile() {
////            super.updateTile();
//////            int x = (int) Mathf.random(this.x - 136, this.x + 136);
//////            int y = (int) Mathf.random(this.y - 136, this.y + 136);
//////            Fx.greenBomb.at(x, y);
//////            Sounds.plasmaboom.at(x, y);
////            if (timer.get(4.5f)) {
////                new Effect(40f, 100f, e -> {
////                    color(Pal.heal);
////                    stroke(e.fout() * 2f);
////                    float circleRad = 4f + e.finpow() * 65f;
//////                    Lines.circle(e.x, e.y, circleRad);
////
////                    color(Pal.heal);
//////                    for (int i = 0; i < 4; i++) {
//////                        Drawf.tri(e.x, e.y, 6f, 100f * e.fout(), i * 90);
//////                    }
////
////                    Lines.stroke(3);
////                    Lines.circle(e.x, e.y, 4f);
////
//////                color();
//////                for (int i = 0; i < 4; i++) {
//////                    Drawf.tri(e.x, e.y, 3f, 35f * e.fout(), i * 90);
//////                }
////
////                    Drawf.light(e.x, e.y, circleRad * 1.6f, Pal.heal, e.fout());
////                }).at(this.x, this.y);
////            }
////        }
//    }
//
////    boolean getEfficiency(int tx, int ty, int rotation, @Nullable Intc2 cpos){
////        float eff = 0f;
////        int cornerX = tx - (size-1)/2, cornerY = ty - (size-1)/2, s = size;
////
////        for(int i = 0; i < size; i++){
////            int rx = 0, ry = 0;
////
////            switch(rotation){
////                case 0 -> {
////                    rx = cornerX + s;
////                    ry = cornerY + i;
////                }
////                case 1 -> {
////                    rx = cornerX + i;
////                    ry = cornerY + s;
////                }
////                case 2 -> {
////                    rx = cornerX - 1;
////                    ry = cornerY + i;
////                }
////                case 3 -> {
////                    rx = cornerX + i;
////                    ry = cornerY - 1;
////                }
////            }
////
////            if(cpos != null){
////                cpos.get(rx, ry);
////            }
////
////            Tile other = world.tile(rx, ry);
////            if(other != null && other.solid()){
////                if (other.block() == Blocks.stoneWall) {
////                    return true;
////                }
////            }
////        }
////        return false;
////    }
//}
