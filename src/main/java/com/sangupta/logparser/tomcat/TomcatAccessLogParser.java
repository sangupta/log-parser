package com.sangupta.logparser.tomcat;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.IPAddress;
import com.sangupta.logparser.common.StringTokenReader;

public class TomcatAccessLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd/MMM/yyyy:hh:mm:ss Z";

    @Override
    public String readLogLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    @Override
    public TomcatAccessLogLine parseLogLine(String logLine) {
        if(AssertUtils.isEmpty(logLine)) {
            return null;
        }
        
        TomcatAccessLogLine line = new TomcatAccessLogLine();
        
        StringTokenReader reader = new StringTokenReader(logLine);
        if(reader.hasNext()) {
            line.clientIP = IPAddress.fromString(reader.readTillNext('-'));
        }
        
        if(reader.hasNext()) {
            line.timestamp = LogParserUtils.parseIntoTime(DATE_PATTERN, reader.readBetween('[', ']'), -1);
        }
        
        if(reader.hasNext()) {
            line.request = HttpRequest.fromString(reader.readBetween('"', '"'));
        }
        
        line.statusCode = LogParserUtils.asInt(reader.readTillNext(' ', 2));
        line.responseSize = LogParserUtils.asInt(reader.getRemaining(), 0);
        
        return line;
    }

}
