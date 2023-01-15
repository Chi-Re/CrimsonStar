package chire.world.blocks.defense.turrets;


import arc.struct.Seq;
import mindustry.Vars;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.turrets.PowerTurret;

import static mindustry.Vars.state;

public class LastResortTurret extends PowerTurret {
    private static InitiateUI.CRUI.Function function = new InitiateUI.CRUI.Function();
    private static final Seq<Tile> spawner = Vars.spawner.getSpawns();
    private static final float damage = 45, cooldown = 120, tileDamage = 24;

    public LastResortTurret(String name) {
        super(name);
    }

    public class LastResortTurretBuild extends PowerTurretBuild{
        public boolean kill = false;
        public float num = 10f, time = 60f * num;
        public String showlabel = null;

        @Override
        public void updateTile() {
            unit.ammo(power.status * unit.type().ammoCapacity);
//            if (isShooting()) {
//                function.showToast(Icon.adminSmall, 32, "[red]▲警告▲\n[white]<最终手段>已启动,等着上军事法庭吧");
//            }

//            if (this.enabled) {
//                if (spawner.size > 0) {
//                    int random = Mathf.random(0, spawner.size - 1);
//                    float sx = spawner.get(random).x * 8 + Mathf.range(2 * 8);
//                    float sy = spawner.get(random).y * 8 + Mathf.range(2 * 8);
//                    Sounds.spark.at(this);
//                    unit.damage(damage);
//                    this.damage(tileDamage);
//                    Fx.chainLightning.at(this.x, this.y, 0, Pal.gray, unit);
//                } else {
//                    this.kill();
//                }
//            }




            super.updateTile();
//            if (Vars.state.teams.cores(this.team).size > 1) {
//                kill = true;
//                showlabel = "[red]▲警告▲\\n[white]<最终手段>已启动,等着上军事法庭吧";
//            }
//            if (kill) {
//                if (!Vars.headless) {
//                    //showlabel = "[red]     Data uplink congestion\nThe central database is overloaded\n     Force restart countdown\n\nTime remaining:" + time;
//                    showlabel = "[red]自爆倒计时\nC:" + (int)time/60;
//                }
//                time--;
//                if (time == 0) {
//                    this.kill();
//                }
//            }
//            if (showlabel != null) {
//                Vars.ui.showLabel(showlabel, 0.015f, this.x, this.y);
//            }
//            showlabel = null;
        }
    }
}
