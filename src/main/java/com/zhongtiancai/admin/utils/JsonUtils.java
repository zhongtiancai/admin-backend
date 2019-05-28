package com.zhongtiancai.admin.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Json工具类 <br/>
 * 屏蔽所有异常
 *
 * @author hbq
 * @dateTime 2017/9/30 10:11
 */
public class JsonUtils {

	private static final transient Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

	static {
		OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public static ObjectMapper getMapper() {
		return OBJECT_MAPPER;
	}

	/**
	 * @author hbq
	 * @dateTime 2017/11/6 10:46
	 * @see ObjectMapper#writeValueAsString(Object)
	 */
	public static String writeValueAsString(Object value) {
		return writeValueAsString(value, false);
	}

	/**
	 * @author hbq
	 * @dateTime 2017/11/6 10:46
	 * @see ObjectMapper#writeValueAsString(Object)
	 * @see ObjectWriter#writeValueAsString(Object)
	 */
	public static String writeValueAsString(Object value, boolean prettyPrint) {
		try {
			return prettyPrint ? OBJECT_WRITER.writeValueAsString(value) : OBJECT_MAPPER.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			LOG.debug("JSON序列化失败", e);
		}
		return null;
	}

	/**
	 * json序列化，可排除字段
	 * xxx
	 *
	 * @author hbq
	 * @dateTime 2017/11/27 12:03
	 */
	public static String writeValueAsString(Object value, boolean prettyPrint, String[] excludeProperties) {
		try {
			FilterProvider filters = new SimpleFilterProvider().addFilter("filter properties by name",
					SimpleBeanPropertyFilter.serializeAllExcept(excludeProperties));
			ObjectWriter writer = OBJECT_MAPPER.writer(filters);
			if (prettyPrint) {
				writer = writer.withDefaultPrettyPrinter();
			}
			return writer.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			LOG.debug("JSON序列化失败", e);
		}
		return null;
	}
	
	public static <T> T string2Obj(String jsonStr, Class<T> objClass)
			throws JsonParseException, JsonMappingException, IOException {
		try {
			return OBJECT_MAPPER.readValue(jsonStr, objClass);
        } catch (IOException e) {
            LOG.debug("JSON反序列化失败", e);
            e.printStackTrace();
            return null;
        }
	}
	
	 /**
     * string转object 用于转为集合对象
     * @param str json字符串
     * @param collectionClass 被转集合class
     * @param elementClasses 被转集合中对象类型class
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = getCollectionType(collectionClass,elementClasses);
        try {
            return OBJECT_MAPPER.readValue(str,javaType);
        } catch (IOException e) {
            LOG.debug("JSON反序列化失败", e);
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static <T> List<T> string2List(String str, Class<T> objClass) {
    	return string2Obj(str,List.class,objClass);
    }
    
    
	
	/**   
     * 获取泛型的Collection Type  
     * @param collectionClass 泛型的Collection   
     * @param elementClasses 元素类   
     * @return JavaType Java类型   
     * @since 1.0   
     */   
	 public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
	     return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	 }
	
	public static String obj2String(Object obj) throws IOException {
		return writeValueAsString(obj);
	}


}
