package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import model.PlayerDTO;

/**
 * A utility class for JSON serialization and deserialization using FastJSON.
 * <p>
 * Provides convenient methods to convert objects to JSON strings and parse JSON strings into objects or arrays.
 * </p>
 */
public class JsonUtil {

  /**
   * Converts an object to its JSON string representation.
   *
   * @param obj the object to be converted to JSON
   * @return the JSON string representation of the object
   */
  public static String toJson(Object obj) {
    return JSON.toJSONString(obj);
  }

  /**
   * Parses a JSON string into an object of the specified class type.
   *
   * @param json  the JSON string to parse
   * @param clazz the class of the object to be returned
   * @param <T>   the type of the returned object
   * @return the parsed object of the specified type
   */
  public static <T> T parseJson(String json, Class<T> clazz) {
    return JSON.parseObject(json, clazz);
  }

  /**
   * Parses a JSON string into a {@link JSONArray}.
   *
   * @param jsonString the JSON string representing an array
   * @return the parsed {@link JSONArray}
   */
  public static JSONArray toArray(String jsonString) {
    return JSONArray.parseArray(jsonString);
  }
}
