package com.sangupta.logparser.charles;

import com.sangupta.jerry.util.AssertUtils;

public class CharlesTransaction {

	public String status;
	
	public String method;
	
	public String protocolVersion;
	
	public String protocol;

	public String host;
	
	public int actualPort;
	
	public String remoteAddress;
	
	public String clientAddress;
	
	public String startTime;
	
	public long startTimeMillis;
	
	public String endTime;
	
	public long endTimeMillis;
	
	public int duration;
	
	public int dnsDuration;
	
	public int connectDuration;
	
	public int overallSpeed;
	
	public long totalSize;
	
	public String path;
	
	public String requestBeginTime;
	
	public long requestBeginTimeMillis;
	
	public int requestDuration;
	
	public int responseDuration;
	
	public String requestTime;
	
	public long requestTimeMillis;
	
	public String responseTime;
	
	public long responseTimeMillis;
	
	public int latency;
	
	public int responseSpeed;
	
	public CharlesRequest request;
	
	public CharlesResponse response;

	@Override
	public String toString() {
		if(AssertUtils.isEmpty(this.path)) {
			return this.method + " " + this.protocol + "://" + this.host;
		}
		
		return this.method + " " + this.protocol + "://" + this.host + this.path;
	}
}
