package com.iwanvi.nvwa.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final SerializerFeature[] features = {SerializerFeature.SortField,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
//            SerializerFeature.WriteNullNumberAsZero,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteDateUseDateFormat
    };

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
//        mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//        mapper.getSerializationConfig().without(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES);
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
                if(value instanceof String){
                    jgen.writeString("");
                }else {
                    jgen.writeNull();
                }
            }
        });
        // 设置全局的NullValueSerializer
    }

	public static String STATUS_OK(String code, String message, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", message);
		return JSON.toJSONString(map, features);
	}

	public static String STATUS_OK() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_SUCCESS);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

	public static String STATUS_OK(Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_SUCCESS);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
//		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

    public static String STATUS_OK(Object data, boolean isAdmin) throws IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_SUCCESS);
        map.put("data", data);
        map.put("timestamp", sdf.format(new Date()));
//		map.put("message", PropertyGetter.getString(Constants.RESPONSE_SUCCESS, StringUtils.EMPTY));
        if(isAdmin){
            return mapper.writeValueAsString(map);
        }
        return JSON.toJSONString(map, features);
    }

	public static String STATUS_OK(String code, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", data);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(code, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

    public static String STATUS_FAILED() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_FAILED);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(Constants.RESPONSE_FAILED, StringUtils.EMPTY));
        return JSON.toJSONString(map, features);
    }

    public static String STATUS_FAILED(String code) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(code, StringUtils.EMPTY));
        return JSON.toJSONString(map, features);
    }

    public static String STATUS_FAILED(String code, String msg) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", StringUtils.isBlank(msg) ? PropertyGetter.getString(code, StringUtils.EMPTY) : msg);
        return JSON.toJSONString(map, features);
    }

    public static String STATUS_INVALID() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", Constants.RESPONSE_INVALID);
        map.put("timestamp", sdf.format(new Date()));
        map.put("message", PropertyGetter.getString(Constants.RESPONSE_INVALID, StringUtils.EMPTY));
        return JSON.toJSONString(map, features);
    }

	public static String DATA_NOT_FIND() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_NOT_FOND);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_NOT_FOND, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

	public static String PARAMETER_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_PARAM_INVALID);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_PARAM_INVALID, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}
	
	public static String PARAMETER_ERROR(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.PARAM_ERROR);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return JSON.toJSONString(map, features);
	}
	public static String ADVERTISER_REJECT(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.ADVERTISER_REJECT);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.ADVERTISER_REJECT, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return JSON.toJSONString(map, features);
	}

	public static String NO_ADVERTISER(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.NO_ADVERTISER);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return JSON.toJSONString(map, features);
	}
	
	
	public static String NO_ENTITY(Object data){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.NO_ENTITY);
		map.put("data", data);
		map.put("message", PropertyGetter.getString(Constants.PARAM_ERROR, StringUtils.EMPTY));
		map.put("timestamp", sdf.format(new Date()));
		return JSON.toJSONString(map, features);
	}
	public static String EXCEPTION_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_EXCEPTION);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_EXCEPTION, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

	public static String SIGN_ERROR() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_FALIED_SIGN_INVALID);
		map.put("timestamp", sdf.format(new Date()));
		map.put("message", PropertyGetter.getString(Constants.RESPONSE_FALIED_SIGN_INVALID, StringUtils.EMPTY));
		return JSON.toJSONString(map, features);
	}

	public static String TO_JSON(Object data) {
		return JSON.toJSONString(data);
	}
	
	public static <T> T TO_OBJ(String source, Class<T> clazz){
		return JSON.parseObject(source, clazz);
	}

    public static <T> T TO_OBJ(Object source, Class<T> clazz){
        return TO_OBJ(JSON.toJSONString(source), clazz);
    }

    public static <T> T parse2Generic(String source){
        return JSON.parseObject(source, new TypeReference<T>(){});
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse2Map(String source){
        Map<String, Object> map = null;
        try {
            map = (Map<String, Object>)TO_OBJ(source, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	public static Map<Integer, Object> parse2MapForIntKey(String source){
		Map<Integer, Object> map = null;
		try {
			map = (Map<Integer, Object>)TO_OBJ(source, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

    @SuppressWarnings("unchecked")
    public static Map<String, Object> parse2Map(InputStream is){
        Map<String, Object> map = null;
        try {
            map = (Map<String, Object>)mapper.readValue(is, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
	public static String STATUS_OK(Object data, String callback) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", Constants.RESPONSE_SUCCESS);
		map.put("data", data);
		return handleResult(map, callback);
	}
	public static String handleResult(Object data, String callback) {
		String json = JSON.toJSONString(data, features);
		if (StringUtils.isNotBlank(callback)) {
			return StringUtils.concat(callback, "(", json, ")");
		} else {
			return json;
		}
	}
	
	public static void main(String[] args) {
//		Map<String, Object> map = new HashMap<String, Object>();
//		
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
//		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
//		list.add("f4db850126b5f52647f5cb020ed794e0d6a0a070351f17720f7ebbbb");
//		
//		map.put("list", list);
//		map.put("pageSize", 3);
//		map.put("pageTotal", 5);
//		
//		String string = STATUS_OK(map);
//		System.out.println("string=" + string);
		
		boolean isTrue = false;
        isTrue |= true;
        System.out.println(isTrue);
	}
}
