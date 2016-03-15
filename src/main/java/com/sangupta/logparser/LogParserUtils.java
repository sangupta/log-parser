package com.sangupta.logparser;

import com.sangupta.jerry.util.StringUtils;

public class LogParserUtils {
    
    public static int asInt(String str) {
        return asInt(str, 0);
    }
            

    public static int asInt(String str, int defaultValue) {
        if(str == null) {
            return defaultValue;
        }
        
        str = str.trim();
        return StringUtils.getIntValue(str, defaultValue);
    }
    
    public static long asLong(String str) {
        return asLong(str, 0l);
    }
    
    public static long asLong(String str, long defaultValue) {
        if(str == null) {
            return defaultValue;
        }
        
        str = str.trim();
        return StringUtils.getLongValue(str, defaultValue);
    }

}
