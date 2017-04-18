package com.mws.core.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * AES加密工具类
 *
 * @author xingkong1221
 * @date 2015-05-19
 */
public class AESUtilsTest {

    @Test
    public void encode() {
        Assert.assertTrue("J5nUYAzAUocmFF9V22f0Jw==".equals(AESUtils.encode(AESUtils.key, "123456")));
    }

    @Test
    public void decode() {
        Assert.assertTrue("123456".equals(AESUtils.decode(AESUtils.key, "J5nUYAzAUocmFF9V22f0Jw==")));
    }
}
