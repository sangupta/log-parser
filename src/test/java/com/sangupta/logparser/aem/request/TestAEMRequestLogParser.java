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

package com.sangupta.logparser.aem.request;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;
import com.sangupta.logparser.common.HttpVerb;

/**
 * Unit tests for {@link AEMRequestLogParser}.
 * 
 * @author sangupta
 *
 */
public class TestAEMRequestLogParser {
	
	@Test
	public void testParser() {
		LogParser parser = LogParserFactory.getParser(LogParsers.Adobe_Experience_Manager_Request_Logs);
		
		// basic sanity
		Assert.assertNotNull(parser);
		Assert.assertTrue(parser instanceof AEMRequestLogParser);
		
		// parse now - inbound request
		String line = "11/Feb/2016:00:00:03 +0530 [300] -> POST /bin/receive?sling:authRequestLogin=1&binaryless=true HTTP/1.1";
		LogLine logLine = parser.parseLogLine(line);
		
		Assert.assertNotNull(logLine);
		Assert.assertTrue(logLine instanceof AEMRequestLogLine);
		
		AEMRequestLogLine requestLine = (AEMRequestLogLine) logLine;
		
		Assert.assertEquals(1455129003000l, requestLine.timestamp);
		Assert.assertEquals(0, requestLine.processingTimeInMillis);
		Assert.assertEquals(300, requestLine.requestNumber);
		Assert.assertEquals(0, requestLine.statusCode);
		Assert.assertEquals(true, requestLine.inBound);
		Assert.assertEquals(false, requestLine.outBound);
		Assert.assertEquals(null, requestLine.mime);
		Assert.assertNotNull(requestLine.request);
		
		Assert.assertEquals(HttpVerb.POST, requestLine.request.verb);
		Assert.assertEquals("/bin/receive?sling:authRequestLogin=1&binaryless=true", requestLine.request.endPoint);
		Assert.assertEquals("HTTP/1.1", requestLine.request.httpVersion);

		// parse now - outbound response
		line = "11/Feb/2016:00:00:09 +0530 [318] <- 200 text/plain 308ms";
		logLine = parser.parseLogLine(line);
		
		Assert.assertNotNull(logLine);
		Assert.assertTrue(logLine instanceof AEMRequestLogLine);
		
		requestLine = (AEMRequestLogLine) logLine;
		
		Assert.assertEquals(1455129009000l, requestLine.timestamp);
		Assert.assertEquals(308, requestLine.processingTimeInMillis);
		Assert.assertEquals(318, requestLine.requestNumber);
		Assert.assertEquals(200, requestLine.statusCode);
		Assert.assertEquals(false, requestLine.inBound);
		Assert.assertEquals(true, requestLine.outBound);
		Assert.assertEquals("text/plain", requestLine.mime);
		Assert.assertNull(requestLine.request);
	}

}
