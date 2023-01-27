package chire.world.blocks.storage;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.Team;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;


public class LimitedCoreBlock extends CoreBlock {
    public LimitedCoreBlock(String name) {
        super(name);
    }

    public void setStats() {
        super.setStats();
        stats.add(Stat.buildTime, this.buildCost / 60, StatUnit.seconds);
    }
    public boolean canBreak(Tile tile) {
        return Vars.state.teams.cores(tile.team()).size > 1;
    }
    public boolean canReplace(Block other) {
        return other.alwaysReplace;
    }
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return Vars.state.teams.cores(team).size < 10;
    }

    public class LimitedCoreBuild extends CoreBuild {
        public boolean kill = false;
        public float num = 3f, time = 60f * num;
        public String showlabel = null;

        @Override
        public void updateTile(){
            iframes -= Time.delta;
            thrusterTime -= Time.delta/90f;


            super.updateTile();
            if (Vars.state.teams.cores(this.team).size > 9) {
                kill = true;
            }
            if (Vars.state.teams.cores(this.team).size > 8){
                showlabel = Core.bundle.get("warning");
            }
            if (kill) {
                if (!Vars.headless) {
                    //showlabel = "[red]     Data uplink congestion\nThe central database is overloaded\n     Force restart countdown\n\nTime remaining:" + time;
                    showlabel = Core.bundle.get("duct") + (int)time/60;
                }
                time--;
                if (time == 0) {
                    this.kill();
                }
            }
            if (showlabel != null) {
                Vars.ui.showLabel(showlabel, 0.015f, this.x, this.y);
            }
            showlabel = null;
        }

        @Override
        public void draw(){
            //draw thrusters when just landed
            if(thrusterTime > 0){
                float frame = thrusterTime;

                Draw.alpha(1f);
                drawThrusters(frame);
                Draw.rect(block.region, x, y);
                Draw.alpha(Interp.pow4In.apply(frame));
                drawThrusters(frame);
                Draw.reset();

                drawTeamTop();
            }else{
                super.draw();
                Draw.z(Layer.effect);
                Lines.stroke(2, Color.valueOf("FF5B5BFF"));
                Draw.alpha(kill ? 1 : Vars.state.teams.cores(this.team).size > 8 ? 1 : 0);
                Lines.arc(this.x, this.y, 16, time * (6 / num) / 360, 90);
            }
        }
        @Override
        public boolean canPickup(){
            return true;
        }
    }
}
