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

package com.sangupta.logparser.elb;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.io.AdvancedStringReader;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.IPAddress;

/**
 * A {@link LogParser} implementation for Amazon AWS Elastic-Load-Balancer
 * access logs.
 * 
 * @author sangupta
 *
 */
public class ElbLogParser implements LogParser {
    
    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

	private static final char SPACE = ' ';

	@Override
	public String readLogLine(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	@Override
	public ElbLogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		ElbLogLine elbLogLine = new ElbLogLine();
		
		AdvancedStringReader reader = new AdvancedStringReader(logLine);
		if(reader.hasNext()) {
			elbLogLine.timestamp = parseElbTimestamp(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.elbName = reader.readTillNext(SPACE);
		}
		
		if(reader.hasNext()) {
			elbLogLine.client = IPAddress.fromString(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.backend = IPAddress.fromString(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.requestTime = parseTime(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.backendTime = parseTime(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.responseTime = parseTime(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.elbStatusCode = Integer.parseInt(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.backendStatusCode = Integer.parseInt(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.receivedBytes = Long.parseLong(reader.readTillNext(SPACE));
		}
		
		if(reader.hasNext()) {
			elbLogLine.sentBytes = Long.parseLong(reader.readTillNext(SPACE));
		}
		
		elbLogLine.request = HttpRequest.fromString(reader.readRemaining());
		
		return elbLogLine;
	}

	private long parseTime(String timeStr) {
		Double d = Double.parseDouble(timeStr);
		d *= (1000d * 1000d);
		return d.longValue();
	}

	private long parseElbTimestamp(String date) {
		long time = LogParserUtils.parseIntoTime(DATE_PATTERN, date, -1);
		if(time < 0) {
		    return time;
		}
		
		// read millis
		int dot = date.indexOf('.');
		String millisString = date.substring(dot + 1, date.length() - 1);
		long millis = Long.parseLong(millisString) / 1000l;
		
		return time + millis;
	}

}
