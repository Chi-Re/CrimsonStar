package chire.content;

import chire.ui.dialogs.CRJoinDialog;

import static mindustry.Vars.ui;

/**覆盖原版*/
public class CROverride {
    public static void overrideBlock(){

    }
    public static void overrideUnit(){

    }
    public static void overrideUI(){
        ui.join = new CRJoinDialog();
    }
    public static void overrideData(){

    }
}
