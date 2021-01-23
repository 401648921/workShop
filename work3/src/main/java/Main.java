import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String[] places = {"China","US","United Kingdom","Japen"};
        APICaller apiCaller = new APICaller();
        for(int i = 0;i<4;i++){
            LinkedHashMap<String, String> map =  JsonUnhand.JsonToMap(apiCaller.result(places[i]));
            CountryCase countryCase = new CountryCase(map.get("All"));
            int id = countryCase.save();
            map.remove("All");
            for(Map.Entry<String,String> entry : map.entrySet()){
                RegionCase regionCase = new RegionCase(entry.getKey(),entry.getValue());
                regionCase.save(id);
            }
        }
    }
}
