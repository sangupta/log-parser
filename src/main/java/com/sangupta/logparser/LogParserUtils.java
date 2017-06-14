/**
 *
 * log-parser: Parsers for various log formats
 * Copyright (c) 2015-2017, Sandeep Gupta
 * 
 * http://sangupta.com/projects/log-parser
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.logparser;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.util.StringUtils;

public class LogParserUtils {
    
    private static final Map<String, FastDateFormat> DATE_FORMATTERS = new HashMap<>();
    
    public static long parseIntoTime(String pattern, String text, long defaultValue) {
        try {
            Date date = getDateFormatter(pattern).parse(text);
            return date.getTime();
        } catch(ParseException e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
    
    public static FastDateFormat getDateFormatter(String pattern) {
        FastDateFormat instance = DATE_FORMATTERS.get(pattern);
        if(instance != null) {
            return instance;
        }
        
        instance = FastDateFormat.getInstance(pattern, TimeZone.getTimeZone("UTC"));
        DATE_FORMATTERS.put(pattern, instance);
        return instance;
    }
    
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

    public static double asDouble(String str) {
        return asDouble(str, 0d);
    }

    public static double asDouble(String str, double defaultValue) {
        if(str == null) {
            return defaultValue;
        }
        
        str = str.trim();
        return StringUtils.getDoubleValue(str, defaultValue);
    }

}
