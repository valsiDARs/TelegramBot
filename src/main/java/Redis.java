import redis.clients.jedis.Jedis;
import java.util.Set;

public class Redis {
    private final Jedis jedis;
    private String key;
    private String value;


    public Redis(){
        jedis = new Jedis(Constant.REDIS_HOST, Constant.REDIS_PORT);
        jedis.auth(Constant.REDIS_PASSWORD);
        System.out.println("Connected to Redis");
    }


    public void pushingDateToRedis(String registerPassword){
        keySplit(registerPassword);
        jedis.set(key,value);
    }

    public String getValue(String key){
        return jedis.get(key);
    }



    public Set<String> getAllKeys(){
        Set<String> keys = jedis.keys("*");

        for (String s : keys) {
            System.out.println(s);
        }
        return keys;
    }

    private void keySplit(String massage){
        String [] keyAndValue = massage.split(" ");
        this.key = keyAndValue[0];
        this.value = keyAndValue [1];
    }
}

