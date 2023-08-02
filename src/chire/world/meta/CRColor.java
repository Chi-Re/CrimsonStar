package chire.world.meta;

import arc.graphics.Color;

public class CRColor {
    public static Color gameYellow = Color.valueOf("FFD37F");

    public Color color;

    public static String getStrRGBA(int RGBA){
        String hex = Integer.toHexString(RGBA);
        if(hex.length() < 8){
            hex = "00000000".substring(0, 8 - hex.length()) + hex;
        }
        return hex;
    }
    public static String getStrRGBA(Color RGBA){
        return getStrRGBA(RGBA.rgba());
    }


    public static String getMessageRGBA(int RGBA){
        return "[#" + getStrRGBA(RGBA) + "]";
    }
    public static String getMessageRGBA(Color RGBA){
        return getMessageRGBA(RGBA.rgba());
    }



    public CRColor(Color color){
        this.color = color;
    }
    public CRColor(int rgba8888){
        this.color = new Color().rgba8888(rgba8888);
    }
    public CRColor(float r, float g, float b, float a){
        this.color = new Color(r, g, b, a);
    }

    public void setColorA(float a){
        this.color.a = a;
    }
}
