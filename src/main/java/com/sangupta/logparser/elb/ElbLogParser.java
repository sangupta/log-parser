package com.sangupta.logparser.elb;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.time.DateParser;
import org.apache.commons.lang3.time.FastDateFormat;

import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.LogParser;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.IPAddress;
import com.sangupta.logparser.common.StringTokenReader;

/**
 * A {@link LogParser} implementation for Amazon AWS Elastic-Load-Balancer
 * access logs.
 * 
 * @author sangupta
 *
 */
public class ElbLogParser implements LogParser {

	private static final DateParser SIMPLE_DATE_PARSER = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss", TimeZone.getTimeZone("UTC"));

	private static final char SPACE = ' ';

	public String readLogLine() {
		return null;
	}

	public LogLine parseLogLine(String logLine) {
		if(AssertUtils.isEmpty(logLine)) {
			return null;
		}
		
		ElbLogLine elbLogLine = new ElbLogLine();
		
		StringTokenReader reader = new StringTokenReader(logLine);
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
		
		elbLogLine.request = HttpRequest.fromString(reader.getRemaining());
		
		return elbLogLine;
	}

	private long parseTime(String timeStr) {
		Double d = Double.parseDouble(timeStr);
		d *= (1000d * 1000d);
		return d.longValue();
	}

	private long parseElbTimestamp(String date) {
		try {
			Date d = SIMPLE_DATE_PARSER.parse(date);
			
			// read millis
			int dot = date.indexOf('.');
			String millisString = date.substring(dot + 1, date.length() - 1);
			long millis = Long.parseLong(millisString) / 1000l;
			
			return d.getTime() + millis;
		} catch (ParseException e) {
			// eat up
		}
		
		return -1;
	}

}
