package workshop17.apiworkshop.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class weatherObj {
    private HashMap coord;
    private ArrayList weather;
    private HashMap main;
    private int visibility;
    private HashMap wind;
    private String timezone;
    private HashMap clouds;
    private String dt;
    private HashMap sys;
    private String id;
    private String name;
    private String cod;

    public weatherObj() {
    }

    public HashMap getCoord() {
        return coord;
    }

    public void setCoord(HashMap coord) {
        this.coord = coord;
    }

    public ArrayList getWeather() {
        return weather;
    }

    public void setWeather(ArrayList weather) {
        this.weather = weather;
    }

    public HashMap getMain() {
        return main;
    }

    public void setMain(HashMap main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public HashMap getWind() {
        return wind;
    }

    public void setWind(HashMap wind) {
        this.wind = wind;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public HashMap getClouds() {
        return clouds;
    }

    public void setClouds(HashMap clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public HashMap getSys() {
        return sys;
    }

    public void setSys(HashMap sys) {
        this.sys = sys;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    @Override
    public String toString() {
        return "weatherObj [clouds=" + clouds + ", cod=" + cod + ", coord=" + coord + ", id=" + id + ", main=" + main
                + ", name=" + name + ", sys=" + sys + ", timezone=" + timezone + ", visibility=" + visibility
                + ", weather=" + weather + ", wind=" + wind + "]";
    }   
}