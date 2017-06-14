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

package com.sangupta.logparser.aem.replication;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;

public class TestAEMReplicationLogParser {

    @Test
    public void test() {
        LogParser parser = LogParserFactory.getParser(LogParsers.Adobe_Experience_Manager_Replication_Logs);
        
        // basic sanity
        Assert.assertNotNull(parser);
        Assert.assertTrue(parser instanceof AEMReplicationLogParser);
        
        // parsing
        String log = "23.01.2016 08:05:37.159 *INFO* [pool-6-thread-30-com_day_cq_replication_job_mmcq-publisher-10-1-0-4(com/day/cq/replication/job/sscq-publisher-10-1-0-4)] com.day.cq.replication.Agent.sscq-publisher-10-1-0-4 >> Path: /content/sangupta/news/just-in/log-parsing-library-is-getting-mature-day-by-day";
        AEMReplicationLogLine logLine = (AEMReplicationLogLine) parser.parseLogLine(log);
        
        Assert.assertNotNull(logLine);
        
        Assert.assertEquals(1453536337159l, logLine.timestamp);
        Assert.assertEquals("INFO", logLine.level);
        Assert.assertEquals("pool-6-thread-30-com_day_cq_replication_job_mmcq-publisher-10-1-0-4(com/day/cq/replication/job/sscq-publisher-10-1-0-4)", logLine.thread);
        Assert.assertEquals("com.day.cq.replication.Agent.sscq-publisher-10-1-0-4", logLine.clazz);
        Assert.assertEquals(">> Path: /content/sangupta/news/just-in/log-parsing-library-is-getting-mature-day-by-day", logLine.message);
    }
    
}
