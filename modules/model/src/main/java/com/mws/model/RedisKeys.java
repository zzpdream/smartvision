package com.mws.model;

public enum RedisKeys {

	AREA_CITY("areaCity", "Area"),

	AREA("area", "Area"),

	WX_USER("wxUser", "WxUser"),

	MEMBER("member", "Member"),

	MEMBER_ACCOUNT("memberAccount", "MemberAccount"),

	USERINFO("areaDistrict", "Area"),

	SELECTED_CABINET_BOX("selectedCabinetBox", "Cabinet"),
	
	SYSTEM_PARAM("param", "Param"),
	
	ORG_PARAM("org", "Organization"),
	
	NEW_ORDER("onLineOrder", "Order"),
	
	USER_WEBCAM_WATCH("onLineUserCam", "UserWebCam"),
	
	WEBCAM("webCam", "WebCam"),
	
	DOOR_ORDER_GO("doorOrderGo", "DoorOrderGo"),
	
	DOOR_ORDER_OVER("doorOrderOver", "DoorOrderOver"),

	;

	public final String key;
	public final String entityName;

	private RedisKeys(String key, String entityName) {
		this.key = key;
		this.entityName = entityName;
	}

	public String getDBEntityKey(Object... suffixs) {
		String keys = key + "_db_entity";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getDBListKey(Object... suffixs) {
		String keys = key + "_db_list";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getCacheEntityKey(Object... suffixs) {
		String keys = key + "_cache_entity";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getCacheListKey(Object... suffixs) {
		String keys = key + "_cache_list";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getDBEntityKeyByUid(Long uid, Object... suffixs) {
		String keys = uid + "_" + key + "_db_entity";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getDBListKeyByUid(Long uid, Object... suffixs) {
		String keys = uid + "_" + key + "_db_list";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getCacheEntityKeyByUid(Long uid, Object... suffixs) {
		String keys = uid + "_" + key + "_cache_entity";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public String getCacheListKeyByUid(Long uid, Object... suffixs) {
		String keys = uid + "_" + key + "_cache_list";
		for (Object suffix : suffixs) {
			keys += "_" + suffix;
		}
		return keys;
	}

	public enum LiveTime {

		SECONDS_15(15 * 1000), SECONDS_30(30 * 1000),

		MINUTES_1(1 * 60 * 1000), MINUTES_2(2 * 60 * 1000), MINUTES_5(5 * 60 * 1000), MINUTES_10(10 * 60 * 1000), MINUTES_30(
				30 * 60 * 1000),

		HOURS_1(1 * 60 * 60 * 1000), HOURS_2(2 * 60 * 60 * 1000), HOURS_5(5 * 60 * 60 * 1000), HOURS_12(
				12 * 60 * 60 * 1000),

		DAYS_1(1 * 24 * 60 * 60 * 1000), DAYS_2(2 * 24 * 60 * 60 * 1000), DAYS_5(5 * 24 * 60 * 60 * 1000), DAYS_15(15
				* 24 * 60 * 60 * 1000),

		;
		public final long time;

		LiveTime(long time) {
			this.time = time;
		}
	}

}
