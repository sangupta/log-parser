package com.sangupta.logparser.gc;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;

import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.exceptions.NotImplementedException;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.common.StringTokenReader;
import com.sangupta.logparser.gc.JavaGarbageCollectionLogLine.GCType;
import com.sangupta.logparser.gc.JavaGarbageCollectionLogLine.JavaGCMemoryRecord;
import com.sangupta.logparser.gc.JavaGarbageCollectionLogLine.JavaGCTimes;

public class JavaGarbageCollectionLogParser implements LogParser {
	
	private static final FastDateFormat DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
	
	@Override
	public String readLogLine(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	@Override
	public JavaGarbageCollectionLogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		char first = logLine.charAt(0);
		if(!Character.isDigit(first)) {
			// TODO: this is a summary line - we can skip this for now
			return null;
		}
		
		// start parsing
		StringTokenReader reader = new StringTokenReader(logLine);
		JavaGarbageCollectionLogLine line = new JavaGarbageCollectionLogLine();
		
		if(reader.hasNext()) {
			line.timestamp = parseTimestamp(reader.readTillNext(": "));
		}
		
		if(reader.hasNext()) {
			line.eventStartTime = Double.parseDouble(reader.readTillNext(':').trim());
		}
		
		if(reader.hasNext()) {
			parseGCMemoryRecords(line, reader.readBetween('[', ']'));
		}
		
		if(reader.hasNext()) {
			parseTimes(line, reader.readBetween('[', ']'));
		}
		
		return line;
	}

	private void parseTimes(JavaGarbageCollectionLogLine line, String str) {
		JavaGCTimes times = new JavaGCTimes();
		line.times = times;
		
		StringTokenReader reader = new StringTokenReader(str);
		
		// go to user
		if(reader.hasNext()) {
			reader.readTillNext("user=");
			times.userTime = Double.parseDouble(reader.readTillNext(' '));
		}
		
		if(reader.hasNext()) {
			reader.readTillNext("sys=");
			times.systemTime = Double.parseDouble(reader.readTillNext(','));
		}
		
		if(reader.hasNext()) {
			reader.readTillNext("real=");
			times.realTime = Double.parseDouble(reader.readTillNext(' '));
		}
	}

	private void parseGCMemoryRecords(JavaGarbageCollectionLogLine line, String str) {
		final int index = str.lastIndexOf(',');
		if(index != -1) {
			int index2 = str.indexOf(' ', index);
			
			// find time
			String time = str.substring(index2).trim();
			index2 = time.indexOf(' ');
			line.gcTime = Double.parseDouble(time.substring(0, index2));
			
			// for the next thing
			str = str.substring(0, index);
		}
		
		if(str.startsWith("GC")) {
			line.gcType = GCType.MinorGC;
			str = str.substring("GC".length());
		} else if(str.startsWith("Full GC")) {
			line.gcType = GCType.FullGC;
			str = str.substring("Full GC".length());
		} else {
			throw new IllegalArgumentException("Unknown GC Type");
		}
		
		StringTokenReader reader = new StringTokenReader(str);

		String record;
		do {
			char peeked = reader.peek();
			if(peeked == 0) {
				break;
			}
			switch(peeked) {
				case '[':
					record = reader.readBetween('[', ']');
					parseMemoryRecordWithName(line, record);
					continue;
					
				case '(':
					throw new NotImplementedException();
					
				default:
					record = reader.readTillNext(')');
					line.heap = parseMemoryRecord(record);
					continue;
			}
		} while(true);
	}
	
	private void parseMemoryRecordWithName(JavaGarbageCollectionLogLine line, String str) {
		int index = str.indexOf(':');
		String name = str.substring(0, index);
		
		JavaGCMemoryRecord record = parseMemoryRecord(str.substring(index + 1).trim());
		if("PSYoungGen".equals(name)) {
			line.youngGeneration = record;
			return;
		}
		
		if("ParOldGen".equals(name)) {
			line.oldGeneration = record;
			return;
		}
		
		if("PSPermGen".equals(name)) {
			line.permanentGeneration = record;
			return;
		}
		
		throw new IllegalArgumentException("Unknown memory record name: " + name);
	}

	private long parseTimestamp(String str) {
		try {
			return DATE_FORMAT.parse(str).getTime();
		} catch (ParseException e) {
			return -1;
		}
	}

	public static JavaGCMemoryRecord parseMemoryRecord(String str) {
		JavaGCMemoryRecord record = new JavaGCMemoryRecord();
		
		int index = str.indexOf('K');
		record.beforeCollection = Integer.parseInt(str.substring(0, index).trim());
		
		index = str.indexOf('>', index);
		int end = str.indexOf('K', index);
		record.afterCollection = Integer.parseInt(str.substring(index + 1, end).trim());
		
		index = str.indexOf('(', end);
		end = str.indexOf('K', index);
		record.totalAvailable = Integer.parseInt(str.substring(index + 1, end).trim());
		
		return record;
	}
}