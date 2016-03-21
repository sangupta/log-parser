package com.sangupta.logparser.aem.audit;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.StringTokenReader;

public class AEMAuditLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss.SSS";
    
    @Override
    public String readLogLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    @Override
    public AEMAuditLogLine parseLogLine(String logLine) {
        if(AssertUtils.isEmpty(logLine)) {
            return null;
        }
        
        AEMAuditLogLine line = new AEMAuditLogLine();
        StringTokenReader reader = new StringTokenReader(logLine);
        if(reader.hasNext()) {
            line.timestamp = LogParserUtils.parseIntoTime(DATE_PATTERN, reader.readTillNext('['), -1);
        }
        
        if(reader.hasNext()) {
            line.eventID = Integer.parseInt(reader.readTillNext(']'));
        }
        
        if(reader.hasNext()) {
            reader.readTillNext(' ');
            line.journalRevisionID = Long.parseLong(reader.readTillNext(' '));
        }
        
        if(reader.hasNext()) {
            line.user = reader.readTillNext('@');
            line.workspace = reader.readTillNext(':');
        }
        
        if(reader.hasNext()) {
            line.nodePath = reader.readTillNext(' ');
        }
        
        if(reader.hasNext()) {
            line.sizeOfUpdate = Integer.parseInt(reader.readBetween('(', ')'));
        }
        
        return line;
    }

}
