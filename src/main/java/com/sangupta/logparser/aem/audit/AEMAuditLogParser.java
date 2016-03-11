package com.sangupta.logparser.aem.audit;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.common.StringTokenReader;

public class AEMAuditLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss.SSS";
    
    private static final FastDateFormat DATE_PARSER = FastDateFormat.getInstance(DATE_PATTERN);

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
            String date = reader.readTillNext('[');
            try {
                line.timestamp = DATE_PARSER.parse(date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
        if(reader.hasNext()) {
            line.eventID = Integer.parseInt(reader.readTillNext(']'));
        }
        
        if(reader.hasNext()) {
            reader.readTillNext(' ');
            line.strangeNumber = Long.parseLong(reader.readTillNext(' '));
        }
        
        if(reader.hasNext()) {
            line.user = reader.readTillNext('@');
            line.workspace = reader.readTillNext(':');
        }
        
        if(reader.hasNext()) {
            line.nodePath = reader.readTillNext(' ');
        }
        
        if(reader.hasNext()) {
            line.randomID = Integer.parseInt(reader.readBetween('(', ')'));
        }
        
        return line;
    }

}
