package workshop17.apiworkshop.models;

public class geoloc {
    String name;
    String country;
    String lat;
    String lon;
    String state;

    public geoloc() {

    }

    public geoloc(String name, String country, String lat, String lon, String state) {
        this.name = name;
        this.country = country;
        this.lat = lat;
        this.lon = lon;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "geoloc [country=" + country + ", lat=" + lat + ", lon=" + lon + ", name=" + name + ", state=" + state
                + "]";
    }

    
}
