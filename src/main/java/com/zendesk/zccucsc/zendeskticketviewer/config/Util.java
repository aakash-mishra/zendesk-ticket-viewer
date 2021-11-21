package com.zendesk.zccucsc.zendeskticketviewer.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

@Component
public class Util {

    public String getBase64EncodedString(String plainTextString) {
        byte[] plainTextBytes = plainTextString.getBytes();
        byte[] base64Bytes = Base64.encodeBase64(plainTextBytes);
        return new String(base64Bytes);
    }

}
