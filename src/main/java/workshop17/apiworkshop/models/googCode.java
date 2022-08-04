package workshop17.apiworkshop.models;

import java.util.ArrayList;
import java.util.HashMap;

public class googCode {
    ArrayList<HashMap> results;

    public googCode() {
        
    }

    public ArrayList<HashMap> getResults() {
        return results;
    }

    public void setResults(ArrayList<HashMap> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "googCode [results=" + results + "]";
    }

}
