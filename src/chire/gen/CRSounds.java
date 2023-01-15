package chire.gen;

import arc.Core;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;

public class CRSounds {

    public static Sound DBZ1;

    public CRSounds() {
    }

    public static Sound loadSound(String soundName,String type) {
        if (!Vars.headless) {
            String name = "sounds/" + soundName + "." + type;
            Sound sound = new Sound();
            Core.assets.load(name, Sound.class, new SoundLoader.SoundParameter(sound));
            return sound;
        } else {
            return new Sound();
        }
    }

    public static void load() {
        DBZ1 = loadSound("DBZ1","ogg");
    }
}
