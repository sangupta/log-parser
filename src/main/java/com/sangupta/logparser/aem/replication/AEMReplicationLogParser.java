package com.sangupta.logparser.aem.replication;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.StringTokenReader;

public class AEMReplicationLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss.SSS";

    @Override
    public String readLogLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    @Override
    public AEMReplicationLogLine parseLogLine(String logLine) {
        if(AssertUtils.isEmpty(logLine)) {
            return null;
        }
        
        AEMReplicationLogLine line = new AEMReplicationLogLine();
        StringTokenReader reader = new StringTokenReader(logLine);
        if(reader.hasNext()) {
            String date = reader.readTillNext('*');
            line.timestamp = LogParserUtils.parseIntoTime(DATE_PATTERN, date, -1);
        }
        
        if(reader.hasNext()) {
            line.level = reader.readTillNext('*');
        }
        
        if(reader.hasNext()) {
            line.thread = reader.readBetween('[', ']');
        }
        
        if(reader.hasNext()) {
            line.clazz = reader.readTillNext(' ', 2).trim();
        }
        
        line.message = reader.getRemaining();
        return line;
    }

}
