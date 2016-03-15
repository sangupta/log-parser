package com.sangupta.logparser.aem.tar;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.TimeZone;

import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.StringTokenReader;

public class AEMTarOptimizationLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss.SSS";
    
    private static final FastDateFormat DATE_PARSER = FastDateFormat.getInstance(DATE_PATTERN, TimeZone.getTimeZone("UTC"));

    @Override
    public String readLogLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    @Override
    public AEMTarOptimizationLogLine parseLogLine(String logLine) {
        if(AssertUtils.isEmpty(logLine)) {
            return null;
        }
        
        AEMTarOptimizationLogLine line = new AEMTarOptimizationLogLine();
        StringTokenReader reader = new StringTokenReader(logLine);
        if(reader.hasNext()) {
            String date = reader.readTillNext('*');
            try {
                line.timestamp = DATE_PARSER.parse(date).getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
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
        
        if(reader.hasNext()) {
            line.tarFile = reader.readTillNext(" id:");
        }
        
        if(reader.hasNext()) {
            line.id = LogParserUtils.asInt(reader.readTillNext("length:"));
        }
        
        if(reader.hasNext()) {
            line.length = LogParserUtils.asLong(reader.readTillNext("append:"));
        }
        
        if(reader.hasNext()) {
            line.append = LogParserUtils.asLong(reader.readTillNext(' '));
        }
        
        if(reader.hasNext()) {
            line.identityHashCode = LogParserUtils.asLong(reader.readTillNext(' '));
        }
        
        if(reader.hasNext()) {
            reader.readTillNext("optimize:");
            line.optimize = LogParserUtils.asLong(reader.getRemaining());
        }
        
        return line;
    }

}
