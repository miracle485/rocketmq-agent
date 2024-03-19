package top.magic.rocketmqagent.util;

import com.google.gson.Gson;

import java.util.List;

public class GsonUtils {
    private static final Gson DEFAULT = new Gson();


    public static <T> String toJsonString(T obj) {
        return DEFAULT.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> type) {
        return DEFAULT.fromJson(json, type);
    }

}
