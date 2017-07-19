package com.rtj.framework.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.util.TokenBuffer;

public class SerializeUtil{

	
	/**
	 * JSON序列化
	 * 
	 * @param data
	 * @return
	 * @throws Exception 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static String serialize(Object data) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				TokenBuffer buffer = new TokenBuffer(mapper);
				mapper.writeValue(buffer, data);
				return mapper.readTree(buffer.asParser()).toString();
			} catch (IOException e) {
				return getErrorReturns();
			}
	}
	
	/**
	 * JSON反序列化
	 * 
	 * @param data
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */

	public static  <T> T deserialize(String strdata, Class<T> clazz) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(strdata, clazz);
	}
	
	public static <T> List<T> deserializeArray(String jsontxt, Class<T> clazz) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			List<T> list = new ArrayList<>();
			ArrayNode nodes = mapper.readValue(jsontxt, ArrayNode.class);
			for (JsonNode node : nodes) {
				list.add(mapper.readValue(node, clazz));
			}
			return list;
	}
	
	public static String getErrorReturns(){
		return "{'responseCode':'9999','responseDesc':'JSON转换错误！'}";
	}
	

}
