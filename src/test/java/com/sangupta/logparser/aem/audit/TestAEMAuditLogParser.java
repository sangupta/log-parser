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

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;

public class TestAEMAuditLogParser {
    
    @Test
    public void testParser() {
        LogParser parser = LogParserFactory.getParser(LogParsers.Adobe_Experience_Manager_Audit_Logs);
        
        // basic sanity
        Assert.assertNotNull(parser);
        Assert.assertTrue(parser instanceof AEMAuditLogParser);
        
        // parsing
        String log = "11.02.2016 00:00:06.073 [15] 160528878912000 admin@crx.default:/etc/cloudservices/sitecatalyst/statistics/ (10207)";
        AEMAuditLogLine logLine = (AEMAuditLogLine) parser.parseLogLine(log);
        
        Assert.assertNotNull(logLine);
        
        Assert.assertEquals(1455148806073l, logLine.timestamp);
        Assert.assertEquals(15, logLine.eventID);
        Assert.assertEquals(160528878912000l, logLine.journalRevisionID);
        Assert.assertEquals("admin", logLine.user);
        Assert.assertEquals("crx.default", logLine.workspace);
        Assert.assertEquals("/etc/cloudservices/sitecatalyst/statistics/", logLine.nodePath);
        Assert.assertEquals(10207, logLine.sizeOfUpdate);
    }

}
