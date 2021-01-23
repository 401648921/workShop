import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.LinkedHashMap;

public class JsonUnhand {
    public static LinkedHashMap<String, String> JsonToMap(String json){
        return JSON.parseObject(json,new TypeReference<LinkedHashMap<String, String>>(){});
    }

    public static void main(String[] args) {
        APICaller apiCaller = new APICaller();
        System.out.println(JsonToMap(apiCaller.result("China")).values());
    }
}
