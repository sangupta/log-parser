package com.sangupta.logparser.aem.tar;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;

public class TestAEMTarOptimizationLogParser {
    
    @Test
    public void test() {
        LogParser parser = LogParserFactory.getParser(LogParsers.Adobe_Experience_Manager_Tar_Optimization_Logs);
        
        // basic sanity
        Assert.assertNotNull(parser);
        Assert.assertTrue(parser instanceof AEMTarOptimizationLogParser);
        
        // parsing
        String log = "11.02.2016 02:01:26.672 *INFO* [Tar PM Optimization] com.day.crx.persistence.tar.file.TarFile /opt/adobe/author-slave/crx-quickstart/repository/tarJournal/data_00129.tar id:129 length:268440064 append:268439040 525310737 optimize: 5725184";
        AEMTarOptimizationLogLine logLine = (AEMTarOptimizationLogLine) parser.parseLogLine(log);
        
        Assert.assertNotNull(logLine);
        
        Assert.assertEquals(1455156086672l, logLine.timestamp);
        Assert.assertEquals("INFO", logLine.level);
        Assert.assertEquals("Tar PM Optimization", logLine.thread);
        Assert.assertEquals("com.day.crx.persistence.tar.file.TarFile", logLine.clazz);
        Assert.assertEquals("/opt/adobe/author-slave/crx-quickstart/repository/tarJournal/data_00129.tar", logLine.tarFile);
        
        Assert.assertEquals(129, logLine.id);
        Assert.assertEquals(268440064l, logLine.length);
        Assert.assertEquals(268439040l, logLine.append);
        Assert.assertEquals(525310737l, logLine.identityHashCode);
        Assert.assertEquals(5725184l, logLine.optimize);
    }

}
