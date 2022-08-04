package workshop17.apiworkshop.models;

import java.util.HashMap;

public class currConverter {
    private HashMap query;//from, to, amount
    private HashMap info; //timestamp, rate
    String date;
    String result;

    public currConverter() {
    }

    public HashMap getQuery() {
        return query;
    }

    public void setQuery(HashMap query) {
        this.query = query;
    }

    public HashMap getInfo() {
        return info;
    }

    public void setInfo(HashMap info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "currConverter [date=" + date + ", info=" + info + ", query=" + query + ", result=" + result + "]";
    }

}