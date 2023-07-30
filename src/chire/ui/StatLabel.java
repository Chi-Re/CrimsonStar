package chire.ui;

import arc.flabel.FLabel;
import arc.math.Interp;
import arc.scene.actions.Actions;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Align;
import arc.util.Time;
import mindustry.gen.Tex;
import mindustry.graphics.Pal;
import mindustry.ui.Styles;

/**使用臭猫的源代码，爱来自{@link mindustry.ui.dialogs.GameOverDialog}的内部类StatLabel。<br>简单的修改测试，修改了大部分代码*/
public class StatLabel extends Table {
    private float progress = 0;

    public void Label(String stat, String value, float delay){
        setTransform(true);
        setClip(true);
        setBackground(Tex.whiteui);
        setColor(Pal.accent);
        margin(2f);

        FLabel statLabel = new FLabel(stat);
        statLabel.setStyle(Styles.outlineLabel);
        statLabel.setWrap(true);
        statLabel.pause();

        Label valueLabel = new Label("", Styles.outlineLabel);
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

    public StatLabel(String stat, Object value, float delay) {
        if (value.getClass() == String.class || value.getClass() == Integer.class) {
            Label(stat, String.valueOf(value), delay);
        } else if (value.getClass() == Boolean.class) {
            if ((Boolean) value) {
                Label(stat, "[green]就绪", delay);
            } else {
                Label(stat, "[red]未就绪", delay);
            }
        }
    }

    public static void addStat(Table parent, String stat, Object value, float delay){
        parent.add(new StatLabel(stat, value, delay)).top().pad(5).growX().height(50).row();
    }

    public static void addStats(Table parent, float increase, Object... date){
        float delay = 0f;
        for (var i = 0; i<date.length; i += 2) {
            delay += increase;
            addStat(parent, date[i].toString(), date[i+1], delay);
        }
    }

    public static void addStats(Table parent, Object... date){
        addStats(parent, 0.5f, date);
    }
}
