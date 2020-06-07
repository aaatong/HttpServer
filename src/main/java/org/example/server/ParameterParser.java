package org.example.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParameterParser {
    public static Map<String, List<String>> parse(String url) {
        Map<String, List<String>> parameterMap = new HashMap<>();
        if (url.contains("?")) {
            String parameterStr = url.split("\\?")[1];
            String[] kvPairs = parameterStr.split("&");

            for (String kvPair : kvPairs) {
                String key = kvPair.split("=")[0];
                String value = kvPair.split("=")[1];
                if (parameterMap.containsKey(key)) {
                    parameterMap.get(key).add(value);
                } else {
                    List<String> valueList = new ArrayList<>();
                    valueList.add(value);
                    parameterMap.put(key, valueList);
                }
            }
        }
        return parameterMap;
    }
}
