package com.mws.web.web;

import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 异步请求返回实体
 *
 * @author xingkong1221
 * @date 2015-10-10
 */
public class JsonMap extends LinkedHashMap<String, Object> {

    private static final long serialVersionUID = 4868184780459844611L;

    private static final String CODE = "code";

    private static final String MSG = "msg";

    /**
     * 实例化一个空JsonMap对象
     */
    public JsonMap() {
        addAttribute(CODE, 0);
        addAttribute(MSG, "ok");
    }
    /**
     * 实例化一个JsonMap对象
     * @param code 返回码
     * @param msg 返回信息
     */
    public JsonMap(Integer code, String msg) {
        addAttribute(CODE, code);
        addAttribute(MSG, msg);
    }
    /**
     * 实例化一个JsonMap对象
     * @param code 返回码
     * @param msg 返回信息
     * @param attributeName 属性名称
     * @param attributeValue 属性值
     */
    public JsonMap(Integer code, String msg, String attributeName, Object attributeValue) {
        addAttribute(CODE, code);
        addAttribute(MSG, msg);
        addAttribute(attributeName, attributeValue);
    }
    /**
     * 添加指定的属性到对象中
     * @param attributeName 属性名
     * @param attributeValue 属性值
     * @return JsonMap对象
     */
    public JsonMap addAttribute(String attributeName, Object attributeValue) {
        Assert.notNull(attributeName, "属性名称不能为空");
        put(attributeName, attributeValue);
        return this;
    }
    /**
     * 将指定的属性(Map)复制到对象中
     * @param attributes 待复制的属性（Map）
     * @return JsonMap对象
     */
    public JsonMap addAllAttributes(Map<String, ?> attributes) {
        if (attributes != null) {
            putAll(attributes);
        }
        return this;
    }
    /**
     * 将指定的属性(Map)复制到对象中，重名的属性不被替换
     * @param attributes 待复制的属性(Map)
     * @return JsonMap对象
     */
    public JsonMap mergeAttributes(Map<String, ?> attributes) {
        if (attributes != null) {
            for (String key : attributes.keySet()) {
                if (!containsKey(key)) {
                    put(key, attributes.get(key));
                }
            }
        }
        return this;
    }
    /**
     * 根据属性名判断属性是否存在
     * @param attributeName 属性名
     * @return 属性是否存在
     */
    public boolean containsAttribute(String attributeName) {
        return containsKey(attributeName);
    }
}
