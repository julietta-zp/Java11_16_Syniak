package xyz.hedo.shop.controller.command;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomUrlHelper {

    public static Map<String, String> splitQuery(String query) throws UnsupportedEncodingException {
        String pureQuery = query.substring(query.indexOf('?')+1);
        Map<String, String> queryPairs = new LinkedHashMap<>();
        String[] pairs = pureQuery.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            queryPairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return queryPairs;
    }
}
