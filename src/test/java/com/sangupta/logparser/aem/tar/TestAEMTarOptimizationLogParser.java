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
