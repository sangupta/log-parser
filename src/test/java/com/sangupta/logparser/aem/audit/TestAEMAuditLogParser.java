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
        Assert.assertEquals(160528878912000l, logLine.strangeNumber);
        Assert.assertEquals("admin", logLine.user);
        Assert.assertEquals("crx.default", logLine.workspace);
        Assert.assertEquals("/etc/cloudservices/sitecatalyst/statistics/", logLine.nodePath);
        Assert.assertEquals(10207, logLine.randomID);
    }

}
