package com.mws.web.net;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Created by ranfi on 2/23/16.
 */
public class DispatchServerTest extends BaseClientTest {

    private final String API_URL = "http://localhost:9999/mws/api";


    @Test
    public void clearSeat() {
        Map<String, Integer> param = Maps.newHashMap();
        param.put("seatId", 1);
        String result = post(API_URL + "/clearmember", param, String.class);
        System.out.print("out json." + result);
    }
}
