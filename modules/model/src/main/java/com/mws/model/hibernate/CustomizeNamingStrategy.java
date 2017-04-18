package com.mws.model.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.springframework.stereotype.Component;

/**
 * Hibernate 自定义命名策略
 *
 * @author xingkong1221
 * @date 2015-11-17
 */
@Component
public class CustomizeNamingStrategy extends ImprovedNamingStrategy {

    private static final long serialVersionUID = -795107307629437846L;

    /** 表前缀 */
    private static final String TABLE_PREFIX = "eh";

    /**
     * 表名映射
     *
     * @param className 类型
     * @return 表名
     */
    @Override
    public String classToTableName(String className) {
        String[] names = StringUtils.splitByCharacterTypeCamelCase(className);
        StringBuilder b = new StringBuilder(30);
        b.append(TABLE_PREFIX);
        for (String name : names) {
            b.append("_").append(name.toLowerCase());
        }
        return b.toString();
    }

    /**
     * 字段名映射
     *
     * @param propertyName 属性名
     * @return 字段名
     */
    @Override
    public String propertyToColumnName(String propertyName) {
        String[] names = StringUtils.splitByCharacterTypeCamelCase(propertyName);
        StringBuilder b = new StringBuilder(20);
        boolean isFirst = true;
        for (String name : names) {
            if (isFirst) {
                isFirst = false;
            } else {
                b.append("_");
            }
            b.append(name.toLowerCase());
        }
        return b.toString();
    }
}
