package chire.util;

import mindustry.Vars;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class STTFile {
    /**
     * @author 炽热S
     * 没错我为了图方便写的,很好用,但不稳定(注意)
     */
    public void SttFile(File file) {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write("你在看什么");
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
