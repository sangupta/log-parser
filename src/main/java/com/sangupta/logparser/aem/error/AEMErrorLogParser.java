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

package com.sangupta.logparser.aem.error;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.StringUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.common.StringTokenReader;

/**
 * A {@link LogParser} implementation to parse <code>error.log</code>
 * files as generated by Adobe Experience Manager.
 *  
 * @author sangupta
 *
 */
public class AEMErrorLogParser implements LogParser {
	
	/**
	 * The date pattern that is at the beginning of log files
	 * 
	 */
	private static final String DATE_PATTERN = "dd.MM.yyyy";
	
	/**
	 * The {@link FastDateFormat} instance for the {@link #DATE_PATTERN}
	 */
	private static final FastDateFormat DATE_PARSER = FastDateFormat.getInstance(DATE_PATTERN);
	
	/**
	 * The last line that we are holding
	 */
	private transient String lastLine;
	
	@Override
	public String readLogLine(BufferedReader reader) throws IOException {
		do {
			String currentLine = reader.readLine();
			if(currentLine == null) {
				// we have reached the end of file
				String returnLine = this.lastLine;
				this.lastLine = null;
				return returnLine;
			}
			
			// check if we have something in buffer
			if(this.lastLine == null) {
				this.lastLine = currentLine;
				continue;
			}
			
			// check if this line starts with a date
			boolean isAFreshLine = startsWithDate(currentLine);
			if(isAFreshLine) {
				String returnLine = this.lastLine;
				this.lastLine = currentLine;
				return returnLine;
			}
			
			// append this to previous line in buffer
			this.lastLine = this.lastLine + StringUtils.SYSTEM_NEW_LINE + currentLine;
		} while(true);
	}

	/**
	 * Check if the given line starts with a valid date of pattern
	 * {@link #DATE_PATTERN}.
	 * 
	 * @param line
	 *            the line to be analyzed
	 * 
	 * @return <code>true</code> if line starts with a date, <code>false</code>
	 *         otherwise
	 */
	private boolean startsWithDate(String line) {
		if(AssertUtils.isEmpty(line)) {
			return false;
		}
		
		if(line.length() < DATE_PATTERN.length()) {
			return false;
		}
		
		char first = line.charAt(0);
		if(!(first >= '0' && first <= '9')) {
			return false;
		}
		
		String str = line.substring(0, DATE_PATTERN.length());
		try {
			DATE_PARSER.parse(str);
		} catch (ParseException e) {
			// not a valid date
			return false;
		}
		
		return true;
	}

	@Override
	public AEMErrorLogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		AEMErrorLogLine line = new AEMErrorLogLine();
		StringTokenReader reader = new StringTokenReader(logLine);
		if(reader.hasNext()) {
			line.timestamp = parseTimeStamp(reader.readTillNext('*'));
		}
		
		if(reader.hasNext()) {
			line.level = extractLevel(reader.readTillNext('*'));
		}
		
		if(reader.hasNext()) {
			line.thread = reader.readTillNextClosing('[', ']');
			cleanUpThreadName(line);
		}
		
		if(reader.hasNext()) {
			splitMessageAndStackTrace(line, reader.getRemaining());
		}
		
		return line;
	}

	private void cleanUpThreadName(AEMErrorLogLine line) {
		int index = line.thread.indexOf('[');
		line.thread = line.thread.substring(index + 1).trim();
	}

	private void splitMessageAndStackTrace(AEMErrorLogLine line, String text) {
		int index = text.indexOf('\n');
		if(index == -1) {
			line.message = text;
			line.stackTrace = null;
			return;
		}
		
		line.message = text.substring(0, index).trim();
		line.stackTrace = text.substring(index).trim();
	}

	private String extractLevel(String level) {
		if(AssertUtils.isEmpty(level)) {
			return null;
		}
		
		level = level.trim();
		int start = 0;
		int end = level.length();
		if(level.startsWith("*")) {
			start = 1;
		}
		if(level.endsWith("*")) {
			end = 1;
		}
		
		return level.substring(start, end);
	}

	private long parseTimeStamp(String time) {
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS");
		try {
			return format.parse(time.trim()).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

}
