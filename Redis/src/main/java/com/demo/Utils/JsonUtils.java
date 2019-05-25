package com.demo.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Anson
 * @date 2019/5/25
 */
public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final static ObjectMapper OM;

    static {
        OM = new ObjectMapper();
        OM.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OM.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    private JsonUtils() {

    }

    public static String prepareResponseObject(String key, Object obj) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put(key, obj);
        return toJsonString(map);
    }

    public static String toJson(Object obj) throws Exception {
        try {
            return OM.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new Exception(e);
        }
    }

    public static <T> T fromJson(String jsonText, Class<T> clazz) throws Exception {
        try {
            return OM.readValue(jsonText, clazz);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    public static <T> List<T> fromJsonStringToObjectList(String jsonText, Class<T> clazz) throws Exception {
        try {
            return OM.readValue(jsonText, new TypeReference<List<T>>() {});
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * Converting object to Json in byte array (Using Jackson 2).
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static byte[] toByte(Object object) throws Exception {
        try {
            return OM.writeValueAsBytes(object);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static Map<String, Object> fromJson(String jsonString) throws Exception {
        try {
            return OM.readValue(jsonString, new TypeReference<HashMap<String, Object>>() {
            });
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

    /**
     * Converting Map to Json in String (Using Jackson 2).
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static String toJsonString(Map<String, Object> map) throws Exception {
        try {
            return OM.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new Exception(e);
        }
    }
}
