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

package com.sangupta.logparser.aem.tar;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.io.AdvancedStringReader;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;

public class AEMTarOptimizationLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "dd.MM.yyyy hh:mm:ss.SSS";
    
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
        AdvancedStringReader reader = new AdvancedStringReader(logLine);
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
            line.optimize = LogParserUtils.asLong(reader.readRemaining());
        }
        
        return line;
    }

}
