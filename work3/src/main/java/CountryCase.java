import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;


public class CountryCase {
    private int countryId;
    private int confirmed;
    private int recovered;
    private int deaths;
    private String country;
    private int population;
    private int SqKmArea;
    private float lifeExpectancy;
    private int elevationInMeters;
    private String continent;
    private String abbreviation;
    private String location;
    private int iso;
    private String capitalCity;
    //有些国家没有以下属性
    private float lat;
    //有些国家没有以下属性
    private float longe;
    private Date updated;

    public CountryCase(String json)  {
        LinkedHashMap<String, String> map = JSON.parseObject(json,new TypeReference<LinkedHashMap<String, String>>(){});
        //System.out.println(json);
        //System.out.println(map.get("confirmed"));
        //System.out.println(map.get("elevation_in_meters").replace(",",""));
        this.confirmed = Integer.parseInt(map.get("confirmed"));
        this.recovered = Integer.parseInt(map.get("recovered"));
        this.deaths = Integer.parseInt(map.get("deaths"));
        this.country = map.get("country");
        this.population = Integer.parseInt(map.get("population"));
        this.SqKmArea = Integer.parseInt(map.get("sq_km_area"));
        this.lifeExpectancy = Float.parseFloat(map.get("life_expectancy"));
        this.elevationInMeters = Integer.parseInt(map.get("elevation_in_meters").replace(",",""));
        this.continent = map.get("continent");
        this.abbreviation = map.get("abbreviation");
        this.location = map.get("location");
        this.iso = Integer.parseInt(map.get("iso"));
        this.capitalCity = map.get("capital_city");
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
        //System.out.println(this.updated);
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getSqKmArea() {
        return SqKmArea;
    }

    public void setSqKmArea(int sqKmArea) {
        SqKmArea = sqKmArea;
    }

    public float getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(float lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public int getElevationInMeters() {
        return elevationInMeters;
    }

    public void setElevationInMeters(int elevationInMeters) {
        this.elevationInMeters = elevationInMeters;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
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
    //存放到数据库的方法
    public int save(){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try{
            conn = ConnectionFactory.getConnection();
            String sql1 = "select updated from country_case where country=?";
            String sql2 = "delete from country_case where country=?";
            String sql3 = "select location_id from location where location=?";
            String sql4 = "insert into location (location) values (?)";
            String sql5 = "select continent_id from continent where continent=?";
            String sql6 = "insert into continent (continent) values (?)";
            String sql7 = "insert into country_case (confirmed,recovered,deaths,country,population,sq_km_area,life_expectancy,elevation_in_meters,continent_id,abbreviation,location_id,iso,capital_city,lat,longe,updated) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql8 = "select country_id from country_case where country=?";
            //如果时间已经是最新的则结束
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement1.setString(1,this.country);
            rs = preparedStatement1.executeQuery();
            //存在且最新
            if(rs.next()&&(this.updated==null||rs.getDate(1).equals(this.updated))){
                PreparedStatement preparedStatement8 = conn.prepareStatement(sql8);
                preparedStatement8.setString(1,this.getCountry());
                rs = preparedStatement8.executeQuery();
                if(rs.next()){
                    return rs.getInt(1);
                }
                return 0;
            }
            //不存在或者不是最新
            //先删去现有行
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setString(1,this.country);
            preparedStatement2.executeUpdate();
            //寻找外键 locaotion
            PreparedStatement preparedStatement3 = conn.prepareStatement(sql3);
            preparedStatement3.setString(1,this.location);
            rs = preparedStatement3.executeQuery();
            int locationId = 0;
            if(rs.next()){
                locationId = rs.getInt(1);
            }else{
                PreparedStatement preparedStatement4 = conn.prepareStatement(sql4);
                preparedStatement4.setString(1,this.location);
                preparedStatement4.executeUpdate();
                rs = preparedStatement3.executeQuery();
                if(rs.next()){
                    locationId = rs.getInt(1);
                }
            }
            //寻找外键 continent
            PreparedStatement preparedStatement5 = conn.prepareStatement(sql5);
            preparedStatement5.setString(1,this.continent);
            rs = preparedStatement5.executeQuery();
            int continentId = 0;
            if(rs.next()){
                continentId = rs.getInt(1);
            }else{
                PreparedStatement preparedStatement6 = conn.prepareStatement(sql6);
                preparedStatement6.setString(1,this.continent);
                preparedStatement6.executeUpdate();
                rs = preparedStatement5.executeQuery();
                if(rs.next()){
                    continentId = rs.getInt(1);
                }
            }
            PreparedStatement preparedStatement7 = conn.prepareStatement(sql7);
            preparedStatement7.setInt(1,this.getConfirmed());
            preparedStatement7.setInt(2,this.getRecovered());
            preparedStatement7.setInt(3,this.getDeaths());
            preparedStatement7.setString(4,this.getCountry());
            preparedStatement7.setInt(5,this.getPopulation());
            preparedStatement7.setInt(6,this.getSqKmArea());
            preparedStatement7.setFloat(7,this.getLifeExpectancy());
            preparedStatement7.setInt(8,this.getElevationInMeters());
            preparedStatement7.setInt(9,continentId);
            preparedStatement7.setString(10,this.getAbbreviation());
            preparedStatement7.setInt(11,locationId);
            preparedStatement7.setInt(12,this.getIso());
            preparedStatement7.setString(13,this.getCapitalCity());
            preparedStatement7.setFloat(14,this.getLat());
            preparedStatement7.setFloat(15,this.getLonge());
            preparedStatement7.setDate(16,new java.sql.Date(this.getUpdated().getTime()));
            preparedStatement7.executeUpdate();

            //返回自增id
            PreparedStatement preparedStatement8 = conn.prepareStatement(sql8);
            preparedStatement8.setString(1,this.getCountry());
            rs = preparedStatement8.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            return 0;


        }catch (Exception e) {
            System.out.println("保存失败");
            return 0;
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
