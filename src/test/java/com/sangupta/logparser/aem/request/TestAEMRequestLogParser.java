package com.sangupta.logparser.aem.request;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;
import com.sangupta.logparser.common.HttpVerb;

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
