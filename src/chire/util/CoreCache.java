package chire.util;

import arc.Core;
import arc.func.Prov;
import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个用来写入数据到游戏的类,使用{@code putJson}写入<br>
 * 可以顺便使用,记得标作者<br>
 * 注意:记得在使用前备份好游戏数据,防止一些数据错误的填写
 * @author 炽热S
 */
public class CoreCache {

    /**
     *写入数据<br>
     * 可以写很多很多的类型...
     */
    public static void put(String name, Object value) {
        Core.settings.putJson(name, value);
    }

    /**去除数据*/
    public static void remove(String name) {
        Core.settings.remove(name);
    }


    /**
     * 试用前请注意,请确保符合转换的规范,防止报错<br>
     * 例如:<br>
     * {@link String}1-->{@link Integer}1<br>
     * {@link String}1.1-->{@link Float}1.1<br>
     * <br>
     * 这是因为原理上来讲可能出现的问题,注意一下
     */
    public static <T> T get(String name, Class<T> type, Prov<T> def) {
        return Core.settings.getJson(name, type, def);
    }

    public static String getString(String name) {
        return get(name, String.class, String::new);
    }

    public static Boolean getBoolean(String name) {
        return Boolean.valueOf(getString(name));
    }

    public static Integer getInt(String name) {
        return Integer.valueOf(getString(name));
    }

    public static Float getFloat(String name) {
        return Float.valueOf(getString(name));
    }

    public static Object getObject(String name) {
        return get(name, Object.class, Object::new);
    }

    /**json方法,可能不是很好,但可以解决燃眉之寄*/
    public static Map<String, Object> jsonMap() {
        return new HashMap<>();
    }
    public static void jsonPut(Map<String, Object> map, String s, Object o) {
        map.put(s, o);
    }
    public static void jsonPut(Map<String, Object> map, String[] s, Object[] o) {
        for (int i = 0; i < s.length; i++) {
            map.put(s[i], o[i]);
        }
    }
    public static Object jsonGet(Map<String, Object> map, String s) {
        return map.get(s);
    }
    public static JSONObject jsonObject(Map<?, ?> map) {
        return new JSONObject(map);
    }
    public static JSONObject jsonObject(String text) {
        return JSONObject.parseObject(text);
    }
}