package util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class MyDeserializer<T> implements JsonDeserializer<T> {

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		
		JsonElement content = json.getAsJsonObject().get("content");
		
		return new Gson().fromJson(content, typeOfT);
	}


}
