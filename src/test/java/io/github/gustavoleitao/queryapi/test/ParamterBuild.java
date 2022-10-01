package io.github.gustavoleitao.queryapi.test;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ParamterBuild {

    public Map<String, Object> paramters = new HashMap<>();

    private ParamterBuild() {
    }

    public ParamterBuild eq(String key, String value){
        paramters.put(key, value);
        return this;
    }

    public ParamterBuild like(String key, String value){
        paramters.put(key, new HashMap<>(){{put("$like", value);}});
        return this;
    }

    public ParamterBuild gt(String key, String value) {
        paramters.put(key, new HashMap<>(){{put("$gt", value);}});
        return this;
    }

    public ParamterBuild lt(String key, String value) {
        paramters.put(key, new HashMap<>(){{put("$lt", value);}});
        return this;
    }

    public ParamterBuild ge(String key, String value) {
        paramters.put(key, new HashMap<>(){{put("$ge", value);}});
        return this;
    }

    public ParamterBuild le(String key, String value) {
        paramters.put(key, new HashMap<>(){{put("$le", value);}});
        return this;
    }

    public static ParamterBuild instance(){
        return new ParamterBuild();
    }

    public Map<String, String> build(){
        var json = new JSONObject(paramters);
        return new HashMap<>(){{put("query", json.toString());}};
    }

}
