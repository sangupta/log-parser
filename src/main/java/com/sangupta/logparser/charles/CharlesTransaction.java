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
