package chire.ui.dialogs;

import arc.Core;
import arc.func.Floatp;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.scene.Group;
import arc.scene.actions.Actions;
import arc.scene.event.Touchable;
import arc.scene.ui.Label;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Table;
import arc.util.Time;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.WarningBar;

import static chire.ChireJavaMod.Load;
import static chire.ChireJavaMod.getName;
import static chire.world.meta.CRColor.gameYellow;

public class LoadingFragment extends mindustry.ui.fragments.LoadingFragment {
    public boolean TextFlashes = false;
    private Table table;
    private TextButton button;
    private Bar bar;
    private Label nameLabel;
    private float progValue;
    private Color upbarColor = gameYellow.cpy();
    private Color downbarColor = gameYellow.cpy();
    public LoadingFragment() {
        build(Core.scene.root);
    }

    public void build(Group parent) {
        parent.fill(t -> {
            t.rect((x, y, w, h) -> {
                Draw.alpha(t.color.a);
                Styles.black8.draw(0, 0, Core.graphics.getWidth(), Core.graphics.getHeight());
            });
            t.visible = false;
            t.touchable = Touchable.enabled;
            t.add().height(133f).row();
            t.image(Load(getName("icon"))).size(160).pad(15).row();
            WarningBar warningBar = new WarningBar();
            WarningBar warningBar1 = new WarningBar();
            warningBar1.setColor(downbarColor);
            warningBar.setColor(upbarColor);
            t.add(warningBar).growX().height(24f);
            t.row();
            nameLabel = t.add("@loading").update(label -> {
                if (TextFlashes) {
                    label.setColor(Color.grays(nameLabel.color.a = (float) (Math.sin(Time.time * 0.2) * 0.5 + 0.5)));
                }
            }).pad(10f).style(Styles.techLabel).get();
            t.row();
            t.add(warningBar1).growX().height(24f);
            t.row();

            text("@loading");

            bar = t.add(new Bar()).pad(3).padTop(6).size(500f, 40f).visible(false).get();
            t.row();
            button = t.button("@cancel", () -> {
            }).pad(20).size(250f, 70f).visible(false).get();
            table = t;
        });
    }

    public void toFront() {
        table.toFront();
    }

    public void setProgress(Floatp progress) {
        bar.reset(0f);
        bar.visible = true;
        bar.set(() -> ((int) (progress.get() * 100) + "%"), progress, Pal.accent);
    }

    public float getProgValue() {
        return progValue;
    }

    public boolean getlableVisible() {
        return table.visible;
    }

    public void snapProgress() {
        bar.snap();
    }

    public void setProgress(float progress) {
        progValue = progress;
        if (!bar.visible) {
            setProgress(() -> progValue);
        }
    }

    public void setButton(Runnable listener) {
        button.visible = true;
        button.getListeners().remove(button.getListeners().size - 1);
        button.clicked(listener);
    }

    public void setText(String text) {
        text(text);
        setColor(Pal.lancerLaser);
    }

    public void show() {
        show("@loading");
    }

    public void show(String text) {
        TextFlashes = false;
        button.visible = false;
        nameLabel.setColor(Color.white);
        bar.visible = false;
        table.clearActions();
        table.touchable = Touchable.enabled;
        text(text);
        table.visible = true;
        table.color.a = 1f;
        table.toFront();
        progValue = 0f;
    }

    public void show(String text, Color color) {
        TextFlashes = false;
        button.visible = false;
        nameLabel.setColor(Color.white);
        bar.visible = false;
        table.clearActions();
        table.touchable = Touchable.enabled;
        text(text);
        setColor(color);
        setDownbarColor(color);
        table.visible = true;
        table.color.a = 1f;
        table.toFront();
        progValue = 0f;
    }

    public void setDownbarColor(Color color) {
        downbarColor = upbarColor = color;
    }

    public void setColor(Color color) {
        this.nameLabel.setColor(color);
    }

    public void hide() {
        progValue = 0;
        table.clearActions();
        table.toFront();
        table.touchable = Touchable.disabled;
        table.actions(Actions.fadeOut(0.25F), Actions.visible(false));
    }

    private void text(String text) {
        nameLabel.setText(text);

        CharSequence realText = nameLabel.getText();

        for (int i = 0; i < realText.length(); i++) {
            if (Fonts.tech.getData().getGlyph(realText.charAt(i)) == null) {
                nameLabel.setStyle(Styles.defaultLabel);
                return;
            }
        }
        nameLabel.setStyle(Styles.techLabel);
    }
}
