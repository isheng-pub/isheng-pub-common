/**
 * Copyright (c) 2011-2019 All Rights Reserved.
 *
 * @description：
 *
 * @fileName：JosnMapper.java
 * @author：isheng92 
 * @dateTime：2019年6月22日 上午10:46:09
 */
package com.isheng.common.json;

import java.io.IOException;

import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * @description：json工具类
 * 注：因fastjson存在很多写死的代码，而且为了追求快，舍弃了很多标准的东西，可能会造成未知bug
 *
 * @author：isheng92
 * @createTime：2019年6月22日 上午10:46:09
 */
public class JsonMapper extends ObjectMapper {

	private static final long serialVersionUID = -7887828701618956175L;

	private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);
	
	private static JsonMapper mapper;

	public JsonMapper() {
		
	}
	
	/**
	 * @param include 输出时包含属性的风格
	 */
	public JsonMapper(Include include) {
		// 设置输出时包含属性的风格
		if (null != include) {
			this.setSerializationInclusion(include);
		}
		 // 允许单引号、允许不带引号的字段名称
		this.enableSimple();
		// 设置输入时忽略在json字符串中存在但java对象实际没有
		this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString("");
			}
		});
		// 进行html解码
		this.registerModule(new SimpleModule().addSerializer(String.class, new JsonSerializer<String>() {

			@Override
			public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
				gen.writeString(org.apache.commons.text.StringEscapeUtils.escapeHtml4(value));
			}
		}));
		//设置时区
		this.setTimeZone(TimeZone.getDefault());
	}
	
	/**
	 * 允许单引号
	 * 允许不带引号的字段名称
	 */
	public JsonMapper enableSimple() {
		this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return this;
	}

	/**
	 * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合。
	 * 默认会先查找jaxb的annotation，如果找不到再找jackson的。
	 */
	public JsonMapper enableJaxbAnnotaion() {
		JaxbAnnotationModule module = new JaxbAnnotationModule();
		this.registerModule(module);
		return this;
	}

	/**
	 * 设定是否使用Enum的toString函数来读写Enum
	 * 为false 时使用Enum的name()函数来读写enum，默认为false
	 * 注：本函数一定要在Mapper创建后，所有的读写动作之前调用
	 * @return
	 */
	public JsonMapper enableEnumUseToString() {
		this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return this;
	}
	

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
	 */
	public static JsonMapper getInstance() {
		if (null == mapper) {
			mapper = new JsonMapper().enableSimple();
		}
		return mapper;
	}

	/**
	 * 创建只输出初始值被改变的属性到json字符串的mapper,
	 * 最节约的存储方式，建议在内部接口使用
	 * @return
	 */
	public static JsonMapper nonDefaultMapper() {
		if (null == mapper) {
			mapper = new JsonMapper(Include.NON_DEFAULT);
		}
		return mapper;
	}
	
	/**
	 * Object可以是POJO，也可以是Collection或数组
	 * 如果对象为null，返回"null";
	 * 如果集合为空集合，返回"[]"；
	 * @param object
	 * @return
	 */
	public String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}
	
	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 * 
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 * 
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 * @see #fromJson(String, JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return this.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}
	
	/**
	 * 反序列化复杂Collection如List<Bean>, 先使用函數createCollectionType构造类型,然后调用本函数.
	 * @see #createCollectionType(Class, Class...)
	 */
	@SuppressWarnings("unchecked")
	public <T> T fromJson(String jsonString, JavaType javaType) {
		try {
			return (T)this.readValue(jsonString, javaType);
		}  catch (IOException e) {
			logger.warn("pares json String error:" + jsonString, e);
			return null;
		}
	}
	
	/**
	 * 构造泛型的Collection Type如:
	 * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
	 * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
	 */
	public JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
		return this.getTypeFactory().constructParametricType(collectionClass, elementClasses);
	}
	
	/**
	 * 当json里只含有bean的部分属性时，更新一个已存在的bean, 只覆盖部分的属性
	 * @param jsonString
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T update(String jsonString, T object) {
		try {
			return (T) this.readerForUpdating(object).readValue(jsonString);
		} catch (JsonProcessingException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		} catch (IOException e) {
			logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
		}
		return null;
	}
	
	/**
	 * 输出JSONP格式数据
	 * @param functionName
	 * @param object
	 * @return
	 */
	public String toJsonP(String functionName, Object object) {
		return toJson(new JSONPObject(functionName, object));
	}
	
	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return this;
	}
	
	/**
	 * 对象转json字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object) {
		return JsonMapper.getInstance().toJson(object);
	}
	
	public static Object fromJsonString(String jsonString, Class<?> clazz) {
		return JsonMapper.getInstance().fromJson(jsonString, clazz);
	}
	
}
