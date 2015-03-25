package currency.exchange.utils;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtils {

	public static JsonNode parse(String value) {
		if (value == null) {
			return null;
		}
		return Json.parse(value);
	}

	public static <A> A parse(JsonNode node, Class<A> cls) {
		return Json.fromJson(node, cls);
	}

	public static <A> A parse(String json, Class<A> cls) {
		JsonNode node = parse(json);

		if (node != null) {
			return Json.fromJson(node, cls);
		}

		return null;

	}

	public static String toJson(Object object) {
		if (object == null) {
			return null;
		}
		return Json.toJson(object).toString();
	}
}
