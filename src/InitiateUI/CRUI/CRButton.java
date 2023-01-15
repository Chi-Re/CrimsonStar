package InitiateUI.CRUI;

import arc.Core;
import arc.Events;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;

public class CRButton {
    static float GameSpeed = 1.0F;

    private void SetGameSpeed(float SetSpeed) {
        GameSpeed = SetSpeed;
        Time.setDeltaProvider(() -> Math.min(Core.graphics.getDeltaTime() * 60 * GameSpeed, 3 * GameSpeed));
    }

    public void showitems() {
        Table tab = new Table();
        tab.setFillParent(true);

    }

    public void load() {
        if (!Vars.headless) {

            Events.on(EventType.ClientLoadEvent.class, e -> {

            });
        }
    }
}
