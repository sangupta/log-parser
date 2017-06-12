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

package com.sangupta.logparser.aem.request;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.jerry.io.AdvancedStringReader;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.LogParserUtils;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.HttpVerb;

/**
 * A {@link LogParser} that can be used to parse <code>request.log</code> files
 * as produced by Adobe Experience Manager.
 * 
 * @author sangupta
 *
 */
public class AEMRequestLogParser implements LogParser {
    
    private static final String DATE_TIME_PATTERN = "dd/MMM/yyyy:hh:mm:ss Z";

	@Override
	public String readLogLine(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	@Override
	public AEMRequestLogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		AEMRequestLogLine line = new AEMRequestLogLine();
		AdvancedStringReader reader = new AdvancedStringReader(logLine);
		if(reader.hasNext()) {
			line.timestamp = LogParserUtils.parseIntoTime(DATE_TIME_PATTERN, reader.readTillNext('['), -1);
		}
		
		if(reader.hasNext()) {
			line.requestNumber = Integer.parseInt(reader.readTillNext(']'));
		}
		
		if(reader.hasNext()) {
			// just the space
			reader.readTillNext(' ');
		}
		
		if(reader.hasNext()) {
			decipherInBound(line, reader.readTillNext(' '));
		}
		
		if(line.inBound) {
			line.request = new HttpRequest();
			
			if(reader.hasNext()) {
				line.request.verb = HttpVerb.fromString(reader.readTillNext(' '));
			}
			
			if(reader.hasNext()) {
				line.request.endPoint = reader.readTillNext(' ');
			}
			
			if(reader.hasNext()) {
				line.request.httpVersion = reader.readRemaining();
			}
		}
		
		if(line.outBound) {
			if(reader.hasNext()) {
				line.statusCode = Integer.parseInt(reader.readTillNext(' '));
			}
			
			String remain = reader.readRemaining();
			int space = remain.lastIndexOf(' ');
			line.mime = remain.substring(0, space).trim();
			
			line.processingTimeInMillis = extractTimeFromMillis(remain.substring(space).trim());
		}
		
		return line;
	}

	/**
	 * Extract time from a string such as <code>123ms</code>
	 * 
	 * @param str
	 * @return
	 */
	private long extractTimeFromMillis(String str) {
		if(str.endsWith("ms")) {
			str = str.substring(0, str.length() - 2);
		}
		
		return Long.parseLong(str);
	}

	/**
	 * Decipher if the log line represent inbound request or outbound response
	 * 
	 * @param line
	 *            the {@link AEMRequestLogLine} instance that needs to be
	 *            updated
	 * 
	 * @param sign
	 *            the sign that has been extracted from log line
	 */
	private void decipherInBound(AEMRequestLogLine line, String sign) {
		if(sign == null) {
			line.inBound = false;
			line.outBound = false;
			return;
		}
		
		sign = sign.trim();
		if("<-".equals(sign)) {
			line.inBound = false;
			line.outBound = true;
			return;
		}
		
		if("->".equals(sign)) {
			line.outBound = false;
			line.inBound = true;
			return;
		}
		
		// unknown sign
		line.inBound = false;
		line.outBound = false;
	}

}
