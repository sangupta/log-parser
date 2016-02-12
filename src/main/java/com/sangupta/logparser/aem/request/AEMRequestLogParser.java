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

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.HttpVerb;
import com.sangupta.logparser.common.StringTokenReader;

/**
 * A {@link LogParser} that can be used to parse <code>request.log</code> files
 * as produced by Adobe Experience Manager.
 * 
 * @author sangupta
 *
 */
public class AEMRequestLogParser implements LogParser {

	public String readLogLine() {
		return null;
	}

	public LogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		AEMRequestLogLine line = new AEMRequestLogLine();
		StringTokenReader reader = new StringTokenReader(logLine);
		if(reader.hasNext()) {
			line.timestamp = parseTimeStamp(reader.readTillNext('['));
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
				line.request.httpVersion = reader.getRemaining();
			}
		}
		
		if(line.outBound) {
			if(reader.hasNext()) {
				line.statusCode = Integer.parseInt(reader.readTillNext(' '));
			}
			
			if(reader.hasNext()) {
				line.mime = reader.readTillNext(' ');
			}
			
			if(reader.hasNext()) {
				line.processingTimeInMillis = extractTimeFromMillis(reader.getRemaining());
			}
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

	private long parseTimeStamp(String time) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss Z");
		try {
			return format.parse(time.trim()).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

}
