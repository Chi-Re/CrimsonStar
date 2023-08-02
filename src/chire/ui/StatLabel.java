package chire.ui;

import arc.flabel.FLabel;
import arc.graphics.Color;
import arc.math.Interp;
import arc.scene.actions.Actions;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

import static chire.world.meta.CRColor.getMessageRGBA;

/**使用臭猫的源代码，爱来自{@link mindustry.ui.dialogs.GameOverDialog}的内部类StatLabel。<br>简单的修改测试，修改了大部分代码*/
public class StatLabel extends Table {
    private float progress = 0;
    public Color trueColor = Color.green;
    public Color falseColor = Color.red;


    public void Label(String stat, String value, float delay){
        setTransform(true);
        setClip(true);
        setBackground(Tex.whiteui);
        setColor(Pal.accent);
        margin(2f);

        FLabel statLabel = new FLabel(stat);

//        if (statColor != null) statLabel.setColor(statColor);

        statLabel.setStyle(Styles.outlineLabel);
        statLabel.setWrap(true);
        statLabel.pause();

        Label valueLabel = new Label("", Styles.outlineLabel);

//        if (valueColor != null) valueLabel.setColor(valueColor);

        valueLabel.setAlignment(Align.right);

        add(statLabel).left().growX().padLeft(5);
        add(valueLabel).right().growX().padRight(5);

        actions(
                Actions.scaleTo(0, 1),
                Actions.delay(delay),
                Actions.parallel(
                        Actions.scaleTo(1, 1, 0.3f, Interp.pow3Out),
                        Actions.color(Pal.darkestGray, 0.3f, Interp.pow3Out),
                        Actions.sequence(
                                Actions.delay(0.3f),
                                Actions.run(() -> {
                                    valueLabel.update(() -> {
                                        progress = Math.min(1, progress + (Time.delta / 60));
                                        valueLabel.setText(value);
                                        //"" + (int) Mathf.lerp(0, value, value < 10 ? progress : Interp.slowFast.apply(progress))
                                    });
                                    statLabel.resume();
                                })
                        )
                )
        );
    }

    public void SettingColor(Color trueColor, Color falseColor){
        if (trueColor != null) this.trueColor = trueColor;
        if (falseColor != null) this.falseColor = falseColor;
    }

    public StatLabel(String stat, Object value, boolean canColor, float delay) {
        if (value.getClass() == String.class || value.getClass() == Integer.class) {
            Label(stat, String.valueOf(value), delay);
        } else if (value.getClass() == Boolean.class) {
            String t = "";
            String f = "";
            if (canColor){
                t = getMessageRGBA(trueColor.rgba());
                f = getMessageRGBA(falseColor.rgba());
            }
            if ((Boolean) value) {
//                Color color1 = new Color();
//                color1.rgba8888(0x00ff00ff);
//                color1.a;
                Label(stat, t+"就绪", delay);
            } else {
                Label(stat, f+"未就绪", delay);
            }
        }
    }



    public static void addStat(Table parent, String stat, Object value, boolean canColor, float delay){
        parent.add(new StatLabel(stat, value, canColor, delay)).top().pad(5).growX().height(50).row();
    }

    public static void addStats(Table parent, float increase, boolean canColor, Object... date){
        float delay = 0f;
        for (var i = 0; i<date.length; i += 2) {
            delay += increase;
            addStat(parent, date[i].toString(), date[i+1], canColor, delay);
        }
    }

    public static void addStats(boolean canColor, Table parent, Object... date){
        addStats(parent, 0.5f, canColor, date);
    }
    public static void addStats(Table parent, Object... date){
        addStats(true, parent, date);
    }
}
