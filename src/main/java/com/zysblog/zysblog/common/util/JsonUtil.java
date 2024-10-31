package com.zysblog.zysblog.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OM = new ObjectMapper();

    // 初始化objectMapper配置
    static {
        // 序列化的时候序列对象的那些属性, ALWAYS 全部属性
        OM.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 如果是空对象的时候,不抛异常
        OM.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 反序列化时,遇到未知属性会不会报错
        OM.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 转换为Json字符串
     *
     * @param instance java对象实例
     * @return json字符串
     */
    public static <T> String toJson(T instance) {
        if (instance == null) {
            return null;
        }
        String jsonStr = null;
        try {
            jsonStr = OM.writeValueAsString(instance);
        } catch (JsonProcessingException e) {
            LOG.error("[JsonUtil] JSON Parse Error, Error Message -> {}", e.getMessage(), e);
        }
        return jsonStr;
    }

    /**
     * 转换为JsonNode
     *
     * @param jsonStr json字符串
     * @return JsonNode
     */
    public static JsonNode getJsonNode(String jsonStr) throws JsonProcessingException {
        return OM.readTree(jsonStr);
    }

    /**
     * 转换为JsonNode
     *
     * @param file file文件
     * @return JsonNode
     */
    public static JsonNode getJsonNode(File file) {
        JsonNode jsonNode = null;
        try {
            jsonNode = OM.readTree(file);
        } catch (IOException e) {
            LOG.error(
                    "[JsonUtil] Get JsonNode By File Error, Error Message => {}",
                    e.getMessage(),
                    e);
        }
        return jsonNode;
    }

    /**
     * 解析Json串到java对象
     *
     * @param jsonStr json字符串
     * @param clazz   java对象class
     * @return java对象
     */
    public static <T> T fromJson(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        T object = null;
        try {
            object = OM.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            LOG.error("[JsonUtil] JSON Parse Error, Error Message -> {}", e.getMessage(), e);
        }
        return object;
    }

    /**
     * 解析Json串到java对象
     *
     * @param jsonStr json字符串
     * @param clazz   java对象class
     * @return java对象
     */
    public static <T> T fromJson(String jsonStr, TypeReference<T> clazz) {
        if (StringUtils.isBlank(jsonStr)) {
            return null;
        }
        T object = null;
        try {
            object = OM.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            LOG.error("[JsonUtil] JSON Parse Error, Error Message -> {}", e.getMessage(), e);
        }
        return object;
    }
}
