/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.mws.web.common.persistence;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.google.common.collect.Maps;

public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE, NEQ, IN, NOTIN, NULL, NOTNULL
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value != null && StringUtils.isBlank(value.toString())) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length < 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = "";
			for (int i = 1; i < names.length; i++) {
				filedName += names[i];
				if ((i + 1) < names.length) {
					filedName += ".";
				}
			}

			Operator operator = Operator.valueOf(names[0]);
			// 创建searchFilter
			if (isValidDate(value.toString())) {
				try {
					Date date = DateUtils.parseDate((String) value, "yyyy-MM-dd");
					// 查询条件中起始日期
					if (operator.equals(Operator.LT) || operator.equals(Operator.LTE)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						cal.add(Calendar.DAY_OF_YEAR, 1);
						date = cal.getTime();
					}
					// 查询条件中结束日期，要加一天来计算。
					if (operator.equals(Operator.GT) || operator.equals(Operator.GTE)) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						date = cal.getTime();
					}
					value = date;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (isValidDateTime(value.toString())) {
				try {
					Date date = DateUtils.parseDate((String) value, "yyyy-MM-dd HH:mm:ss");
					value = date;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}

	public static boolean isValidDate(String sDate) {
		// String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern2);
			Matcher match = pattern.matcher(sDate);
			return match.matches();
		}
		return false;
	}

	public static boolean isValidDateTime(String sDate) {
		String dataPattern = "^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(dataPattern);
			Matcher match = pattern.matcher(sDate);
			return match.matches();
		}
		return false;
	}
}
