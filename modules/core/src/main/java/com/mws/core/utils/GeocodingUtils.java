package com.mws.core.utils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.mws.core.mapper.JsonMapper;
import com.mws.core.utils.http.HttpUtils;

public class GeocodingUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLocationByAddress(String address, String city) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=w0XEV07izS4LSoKB9L8v9sGN&output=json&address={0}&city={1}";
		String jsonString = HttpUtils.get(MessageFormat.format(url, address, city), "utf-8");
		return JsonMapper.nonDefaultMapper().fromJson(jsonString, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getLocationByAddress2(String address, String city) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=w0XEV07izS4LSoKB9L8v9sGN&output=json&address={0}&city={1}";
		String jsonString = HttpUtils.get(MessageFormat.format(url, address, city), "utf-8");
		Map<String, Object> result =  JsonMapper.nonDefaultMapper().fromJson(jsonString, Map.class);
		if (null != result && Integer.parseInt(result.get("status").toString()) == 0) {
			return (Map<String, Object>) result.get("result");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> geoconv(String lng, String lat) {
		String url = "http://api.map.baidu.com/geoconv/v1/?ak=w0XEV07izS4LSoKB9L8v9sGN&coords={0}&output=json&from=1&to=5";
		String jsonString = HttpUtils.get(MessageFormat.format(url, lng + "," + lat), "utf-8");
		Map<String, Object> result = JsonMapper.nonDefaultMapper().fromJson(jsonString, Map.class);
		if (null != result && Integer.parseInt(result.get("status").toString()) == 0) {
			return ((List<Map<String, Object>>) result.get("result")).get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAddressByLocation(String slng, String slat) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=w0XEV07izS4LSoKB9L8v9sGN&location={0},{1}&output=json&pois=0";
		String jsonString = HttpUtils.get(MessageFormat.format(url, slat, slng), "utf-8");
		return JsonMapper.nonEmptyMapper().fromJson(jsonString, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getAddressByLocation2(String slng, String slat, int pois) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=w0XEV07izS4LSoKB9L8v9sGN&location={0},{1}&output=json&pois={2}";
		String jsonString = HttpUtils.get(MessageFormat.format(url, slat, slng, pois), "utf-8");
		Map<String, Object> result = JsonMapper.nonEmptyMapper().fromJson(jsonString, Map.class);
		if (null != result && Integer.parseInt(result.get("status").toString()) == 0) {
			return (Map<String, Object>) result.get("result");
		}
		return null;
	}

	public static List<Map<String, Object>> searchPlaces(String address, String lng, String lat, int radius) {
		String url = "http://api.map.baidu.com/place/v2/search?query=%s&location=%s&radius=%s&output=json&ak=w0XEV07izS4LSoKB9L8v9sGN";
		String jsonString = HttpUtils.get(String.format(url, address, lat + "," + lng, radius),  "utf-8");
		Map<String, Object> result = JsonMapper.nonEmptyMapper().fromJson(jsonString, Map.class);
		if (null != result && Integer.parseInt(result.get("status").toString()) == 0) {
			return (List<Map<String, Object>>) result.get("results");
		}
		return null;
	}

	public static List<Map<String, Object>> searchPlaces(String address, String region) {
		String url = "http://api.map.baidu.com/place/v2/search?ak=w0XEV07izS4LSoKB9L8v9sGN&output=json&query=%s&page_size=15&page_num=0&scope=1&region=%s";
		String jsonString = HttpUtils.get(String.format(url, address, region),  "utf-8");
		Map<String, Object> result = JsonMapper.nonEmptyMapper().fromJson(jsonString, Map.class);
		if (null != result && Integer.parseInt(result.get("status").toString()) == 0) {
			return (List<Map<String, Object>>) result.get("results");
		}
		return null;
	}

}
