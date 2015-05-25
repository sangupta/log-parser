package com.sangupta.logparser.elb;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.IPAddress;

public class ElbLogLine extends LogLine {

	public String elbName;
	
	public IPAddress client;
	
	public IPAddress backend;
	
	public long requestTime;
	
	public long backendTime;
	
	public long responseTime;
	
	public int elbStatusCode;
	
	public int backendStatusCode;
	
	public long receivedBytes;
	
	public long sentBytes;
	
	public HttpRequest request;
	
	@Override
	public String toString() {
		if(this.request == null) {
			return "";
		}
		
		return request.toString();
	}
	
}
