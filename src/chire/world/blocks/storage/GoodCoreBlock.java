package chire.world.blocks.storage;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.math.Interp;
import arc.math.Mathf;
import arc.struct.EnumSet;
import chire.util.CRUtil;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.TargetPriority;
import mindustry.game.Team;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.payloads.BuildPayload;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;

import static mindustry.Vars.*;
/**@author 炽热S
 * I've created a core class that supports the following operations
 * 1.Detachable
 * 2.have casemate
 * 3.Feel free to place it
 */
public class GoodCoreBlock extends CoreBlock {
    public Block cblock = Blocks.duo;
    public Item citem = Items.copper;
    public int numberitem = 1;
    public boolean canbreak = false;
    public boolean canplace = false;
    public boolean hasbuild = false;
//    public float range = CRUtil.getRange(cblock);
    public GoodCoreBlock(String name){
        super(name);

        solid = true;
        update = true;
        hasItems = true;
        priority = TargetPriority.core;
        flags = EnumSet.of(BlockFlag.core);
        unitCapModifier = 10;
        loopSound = Sounds.respawning;
        loopSoundVolume = 1f;
        drawDisabled = false;
        canOverdrive = false;
        envEnabled |= Env.space;

        //support everything
        replaceable = false;
        //TODO should AI ever rebuild this?
        //rebuildable = false;
    }


    @Override
    public boolean canBreak(Tile tile){
        return canbreak;
    }

    @Override
    public boolean canReplace(Block other){
        //coreblocks can upgrade smaller cores
        return super.canReplace(other) || (other instanceof CoreBlock && size >= other.size && other != this);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation){
        if(tile == null) return false;
        //in the editor, you can place them anywhere for convenience
        if(state.isEditor()) return true;
        if(!state.isEditor()) return canplace;

        CoreBuild core = team.core();

        //special floor upon which cores can be placed
        tile.getLinkedTilesAs(this, tempTiles);
        if(!tempTiles.contains(o -> !o.floor().allowCorePlacement || o.block() instanceof CoreBlock)){
            return true;
        }

        //must have all requirements
        if(core == null || (!state.rules.infiniteResources && !core.items.has(requirements, state.rules.buildCostMultiplier))) return false;

        return tile.block() instanceof CoreBlock && size > tile.block().size;
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        if(world.tile(x, y) == null) return;

        if(!canPlaceOn(world.tile(x, y), player.team(), rotation)){

            drawPlaceText(Core.bundle.get(
                    isFirstTier ?
                            //TODO better message
                            "bar.corefloor" :
                            (player.team().core() != null && player.team().core().items.has(requirements, state.rules.buildCostMultiplier)) || state.rules.infiniteResources ?
                                    "bar.corereq" :
                                    "bar.noresources"
            ), x, y, valid);
        }

        if (hasbuild) {
            super.drawPlace(x, y, rotation, valid);
            Drawf.dashCircle(x * 8 + this.offset, y * 8 + this.offset, CRUtil.getRange(cblock), Pal.accent);
        }
    }


    public class NewCoreBuild extends CoreBuild {
        public BuildPayload p = new BuildPayload(cblock, Team.derelict);
//timer.get(4.5F)
        @Override
        public void updateTile() {
            super.updateTile();

            state.rules.canGameOver = false;

            //Discarded effects, or should I change them to continue using them?
            if (false) {
                int x = (int) Mathf.random(this.x - 136, this.x + 136);
                int y = (int) Mathf.random(this.y - 136, this.y + 136);
                Fx.greenBomb.at(x, y);
                Sounds.plasmaboom.at(x, y);
            }
            if (hasbuild) {
                if (p.build.team != this.team) {
                    p.build.team = this.team;
                }
                p.update(null, this);
                if (p.build.acceptItem(this, citem) && this.team.core().items.get(citem) >= 1) {
                    p.build.handleItem(this, citem);
                    this.team.core().items.remove(citem, numberitem);
                }
                p.set(this.x, this.y, p.build.payloadRotation);
            }
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
                if (hasbuild) {
                    p.draw();
                }
            }

        }

        public void drawSelect() {
            if (hasbuild) {
                super.drawSelect();
                Drawf.dashCircle(this.x, this.y, CRUtil.getRange(cblock), Pal.accent);
            }
        }
//        @Override
//        public void updateTile(){
//            super.updateTile();
//            if (timer.get(4.5f)) {
//                if (health > 0 && health < maxHealth && !((maxHealth - ReturnBlood) < health)) {
//                    health += ReturnBlood;
//                } else if ((maxHealth - ReturnBlood) == health) {
//                    health = maxHealth;
//                }
//            }
//            if (health > maxHealth) {
//                health = maxHealth;
//            }
//        }

    }

}
