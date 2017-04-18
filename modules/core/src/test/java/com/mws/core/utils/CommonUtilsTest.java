package com.mws.core.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Common 工具类
 *
 * @author xingkong1221
 * @date 2015-05-19
 */
public class CommonUtilsTest {

    @Test
    public void compare() {
        Assert.assertTrue(CommonUtils.compareVersion("1.5.6", "1.5.2") > 0);
        Assert.assertTrue(CommonUtils.compareVersion("1.5.6", "1.5.6") == 0);
        Assert.assertTrue(CommonUtils.compareVersion("1.5.6", "1.5.8") < 0);
    }
}
