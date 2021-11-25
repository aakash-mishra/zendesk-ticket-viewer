package com.zendesk.zccucsc.zendeskticketviewer;

import com.zendesk.zccucsc.zendeskticketviewer.config.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UtilTest {
    @Test
    public void testGetBase64EncodedString() {
        Util util = new Util();
        String actualResponse = util.getBase64EncodedString("xyz");
        assertEquals("eHl6", actualResponse);
    }
}
