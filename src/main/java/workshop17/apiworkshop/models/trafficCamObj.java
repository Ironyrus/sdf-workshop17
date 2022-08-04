package workshop17.apiworkshop.models;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class trafficCamObj {
    String timestamp;
    ArrayList<trafficCamObj> items;
    HashMap api_info;
    ArrayList<HashMap> cameras;

    public trafficCamObj() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    

    public HashMap getApi_info() {
        return api_info;
    }

    public void setApi_info(HashMap api_info) {
        this.api_info = api_info;
    }

    public ArrayList<trafficCamObj> getItems() {
        return items;
    }

    public void setItems(ArrayList<trafficCamObj> items) {
        this.items = items;
    }

    public ArrayList<HashMap> getCameras() {
        return cameras;
    }

    public void setCameras(ArrayList<HashMap> cameras) {
        this.cameras = cameras;
    }

    
    
}
