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
