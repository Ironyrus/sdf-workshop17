package workshop17.apiworkshop.app;

import jakarta.json.JsonObject;

public interface bgRepo {
    public void save(String cameraData);

    public void deleteAll();

}