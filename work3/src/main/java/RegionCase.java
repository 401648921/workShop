import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;

public class RegionCase {
    private int regionId;
    private int confirmed;
    private int recovered;
    private int deaths;
    private String region;
    private float lat;
    private float longe;
    private Date updated;

    public RegionCase(String regionName, String json) {
        this.region = regionName;
        LinkedHashMap<String, String> map = JSON.parseObject(json,new TypeReference<LinkedHashMap<String, String>>(){});
        this.confirmed = Integer.parseInt(map.get("confirmed"));
        this.recovered = Integer.parseInt(map.get("recovered"));
        this.deaths = Integer.parseInt(map.get("deaths"));
        if(map.get("lat")!=null)
            this.lat = Float.parseFloat(map.get("lat"));
        if(map.get("long")!=null)
            this.longe = Float.parseFloat(map.get("long"));
        //时间获取，并且去掉+00
        if(map.get("long")!=null){
            String time = map.get("updated");
            //System.out.println(time);
            time = map.get("updated").substring(0,time.length()-3);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            try {
                this.updated = sdf.parse(time);
            } catch (ParseException e) {
                this.updated = null;
                e.printStackTrace();
            }
        }
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLonge() {
        return longe;
    }

    public void setLonge(float longe) {
        this.longe = longe;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public void save(int countryId){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            conn = ConnectionFactory.getConnection();

            String sql1 = "select updated from region_case where region=?";
            String sql2 = "delete from region_case where region=?";
            String sql3 = "insert into region_case (country_id,lat,longn,comfirmed,recoverd,region,deaths,updated) values (?,?,?,?,?,?,?,?)";
            //预查
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement1.setString(1,this.region);
            rs = preparedStatement1.executeQuery();
            if(rs.next()&&(this.updated==null||rs.getDate(1).equals(this.updated))){
                return;
            }
            //删去现有行
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setString(1,this.region);
            preparedStatement2.executeUpdate();
            //保存
            PreparedStatement preparedStatement3 = conn.prepareStatement(sql3);
            preparedStatement3.setInt(1,countryId);
            preparedStatement3.setFloat(2,this.lat);
            preparedStatement3.setFloat(3,this.longe);
            preparedStatement3.setInt(4,this.confirmed);
            preparedStatement3.setInt(5,this.recovered);
            preparedStatement3.setString(6,this.region);
            preparedStatement3.setInt(7,this.deaths);
            preparedStatement3.setDate(8,new java.sql.Date(this.getUpdated().getTime()));
            preparedStatement3.executeUpdate();
        }catch (Exception e) {
            System.out.println("保存失败");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
