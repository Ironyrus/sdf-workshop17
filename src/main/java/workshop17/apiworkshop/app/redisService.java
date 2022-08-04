package workshop17.apiworkshop.app;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import jakarta.json.JsonObject;

//https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html

@Service
public class redisService implements bgRepo {
    
    ArrayList<Integer> keys;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //POST
    @Override
    public void save(String cameraData) {
        int max = 0;
        this.keys = getKeys();
        for (Integer key : keys) {
            if (key > max)
                max = key;
        }        
        max += 1; //When saving a new entry, the latest entry is max key + 1. Eg latest is 5, next is 5 + 1 = 6
        String id = max + ""; //Change from int to String
        redisTemplate.opsForValue().set(id, cameraData);
    }

    @Override
    public void deleteAll() {
        this.keys = getKeys();
        for (Integer key : keys) {
            redisTemplate.delete(key + ""); //Deleting key. Casting from integer to String (Since Redis keys are String) 
        }
        
    }

    //Helper class to get all keys from database
    public ArrayList<Integer> getKeys() {
        Set<String> redisKeys = redisTemplate.keys("*"); //Pattern is * for ALL keys. Getting all keys.
        ArrayList<Integer> keys = new ArrayList<>();
        for (String item : redisKeys) {
            keys.add(Integer.parseInt(item));
        }
        return keys;
    }
    // //GET
    // @Override
    // public Checkers findBoardGame(String boardGame) {
    //     Checkers game = redisTemplate.opsForValue().get(boardGame);
    //     //String game = "test";
    //     System.out.println(game);
    //     return game;
    // }

    // //PUT
    // @Override
    // public int update(final Checkers checkerObj, String Id) {

    //     Checkers  result = (Checkers) redisTemplate.opsForValue().get(Id);

    //     System.out.println("INUPDATEMETHOD: " + result.getId());
    //     if (result.isUpsert())
    //         redisTemplate.opsForValue().setIfAbsent(Id, checkerObj);
    //     else
    //         redisTemplate.opsForValue().setIfPresent(Id, checkerObj);
    //     if (result != null) {
    //         checkerObj.setUpdateCount(result.getUpdateCount() + 1); //Incrementing Update Count
    //         checkerObj.setId(result.getId());
    //         redisTemplate.opsForValue().setIfPresent(Id, checkerObj);
    //         return checkerObj.getUpdateCount();
    //     }
    //     return 0;
    // }

}