package chire.util;

//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import mindustry.Vars;

import mindustry.Vars;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.Turret;

import java.io.File;

//import java.io.*;
//import java.nio.charset.StandardCharsets;

public class CRUtil {

    /**
     * @author lyr
     * 会自动判断是否为炮塔,如果不是炮塔返回0
     * @param block
     * @return range
     */
    public static float getRange(Block block) {
        if (block instanceof Turret) {
            return ((Turret) block).range;
        }
        return 0f;
    }

    /**
     * @author 炽热S
     */
    public static File SFile(String str) {
        return new File(Vars.modDirectory + "/settings/" + str);
    }

//    public static String readJsonFile(String fileName) {
//        String jsonStr = "";
//        try {
//            File jsonFile = new File(fileName);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile), StandardCharsets.UTF_8);
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                sb.append((char) ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    public static void main() {
//        String path = Vars.modDirectory + "/settings.json";
//        String s = readJsonFile(path);
//        JSONObject jobj = JSON.parseObject(s);
//        JSONArray movies = jobj.getJSONArray("RECORDS");//构建JSONArray数组
//        for (Object movie : movies) {
//            JSONObject key = (JSONObject) movie;
//            String name = (String) key.get("测试");
//            System.out.println(name);
//        }
//    }
    //放弃(无用且麻烦)
    //            String datajson = """
//                    {
//                    "测试": true,
//                    "json": "yes!",
//                    "覆盖": true
//                    }
//                    """;

//            String datajson = "[]";
//
//            try {
//            File file = new File(Vars.modDirectory  + "/" + "settings.json");
////            if (file.exists()){
////                datajson = "never get gave you up!";
////            }
//            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
//            writer.write(datajson);
//            writer.flush();
//            writer.close();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            Fi settings = new Fi(Vars.modDirectory + "/settings.json");
//            Jval val = Jval.read(settings.readString());
////            if (val.isArray()) {
//                Jval.JsonArray jsonArray = val.asArray();
//                for (Jval jval : jsonArray) {
//                    try {
//                        File filea = new File(Vars.modDirectory  + "/" + "settingsa.json");
//                        Writer writera = new OutputStreamWriter(new FileOutputStream(filea), StandardCharsets.UTF_8);
//                        writera.write((int) jval.getDouble("模组版本", 0));
//                        writera.flush();
//                        writera.close();
//                    } catch (IOException ex) {
//                        throw new RuntimeException(ex);
//                    }
//                }
////            }

}
