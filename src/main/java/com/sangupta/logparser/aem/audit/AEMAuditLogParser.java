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

package com.sangupta.logparser.aem.audit;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.io.AdvancedStringReader;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;

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
        AdvancedStringReader reader = new AdvancedStringReader(logLine);
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
