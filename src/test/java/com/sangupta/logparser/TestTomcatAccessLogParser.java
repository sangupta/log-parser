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

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.common.HttpVerb;
import com.sangupta.logparser.tomcat.TomcatAccessLogLine;
import com.sangupta.logparser.tomcat.TomcatAccessLogParser;

public class TestTomcatAccessLogParser {
    
    @Test
    public void test() {
        String line = "127.0.0.1 - - [06/Mar/2016:23:54:13 +0000] \"GET /view.html?id=5543b82ce4b015291691dc9e HTTP/1.1\" 200 21918";
        
        LogParser parser = LogParserFactory.getParser(LogParsers.Tomcat_Access_Logs);
        
        // basic sanity
        Assert.assertNotNull(parser);
        Assert.assertTrue(parser instanceof TomcatAccessLogParser);
        
        // parse now - inbound request
        LogLine logLine = parser.parseLogLine(line);
        
        Assert.assertNotNull(logLine);
        Assert.assertTrue(logLine instanceof TomcatAccessLogLine);
        
        TomcatAccessLogLine accessLogLine = (TomcatAccessLogLine) logLine;
        
        Assert.assertNotNull(accessLogLine.clientIP);
        Assert.assertEquals("127.0.0.1", accessLogLine.clientIP.toString());
        
        Assert.assertEquals(1457308453000l, accessLogLine.timestamp);
        
        Assert.assertNotNull(accessLogLine.request);
        Assert.assertEquals(HttpVerb.GET, accessLogLine.request.verb);
        Assert.assertEquals("/view.html?id=5543b82ce4b015291691dc9e", accessLogLine.request.endPoint);
        Assert.assertEquals("HTTP/1.1", accessLogLine.request.httpVersion);
        
        Assert.assertEquals(200, accessLogLine.statusCode);
        Assert.assertEquals(21918, accessLogLine.responseSize);
    }

}
