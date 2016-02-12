/**
 *
 * log-parser: Parsers for various log formats
 * Copyright (c) 2015-2016, Sandeep Gupta
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

package com.sangupta.logparser.elb;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserFactory;
import com.sangupta.logparser.LogParsers;
import com.sangupta.logparser.common.HttpVerb;

/**
 * Unit tests for {@link ElbLogParser} class
 * 
 * @author sangupta
 *
 */
public class TestElbLogParser {

	@Test
	public void testParser() {
		LogParser parser = LogParserFactory.getParser(LogParsers.Amazon_AWS_Elastic_Load_Balancer);
		
		// basic sanity
		Assert.assertNotNull(parser);
		Assert.assertTrue(parser instanceof ElbLogParser);
		
		// parse now - inbound request
		String line = "2015-05-22T09:11:07.207317Z MyDomainApp-ELB 50.112.95.211:28491 10.249.64.208:8080 0.00006 0.014009 0.000026 200 200 93 47 \"HEAD https://somedomain.com/index.html HTTP/1.0\"";
		
		LogLine logLine = parser.parseLogLine(line);
		Assert.assertNotNull(logLine);
		Assert.assertTrue(logLine instanceof ElbLogLine);
		
		ElbLogLine requestLine = (ElbLogLine) logLine;
		
		Assert.assertEquals(1432285867207l, requestLine.timestamp);
		Assert.assertEquals(14009, requestLine.backendTime);
		Assert.assertEquals(93, requestLine.receivedBytes);
		Assert.assertEquals(60, requestLine.requestTime);
		Assert.assertEquals(26, requestLine.responseTime);
		Assert.assertEquals(47, requestLine.sentBytes);
		Assert.assertEquals(200, requestLine.backendStatusCode);
		Assert.assertEquals(200, requestLine.elbStatusCode);
		Assert.assertEquals("MyDomainApp-ELB", requestLine.elbName);
		
		Assert.assertNotNull(requestLine.backend);
		Assert.assertEquals("10.249.64.208:8080", requestLine.backend.toString());
		
		Assert.assertNotNull(requestLine.client);
		Assert.assertEquals("50.112.95.211:28491", requestLine.client.toString());
		
		Assert.assertNotNull(requestLine.request);
		Assert.assertEquals(HttpVerb.HEAD, requestLine.request.verb);
		Assert.assertEquals("https://somedomain.com/index.html", requestLine.request.endPoint);
		Assert.assertEquals("HTTP/1.0", requestLine.request.httpVersion);
	}
	
}
