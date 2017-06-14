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

package com.sangupta.logparser.common;

import com.sangupta.jerry.util.AssertUtils;

public class HttpRequest {
	
	public HttpVerb verb;
	
	public String endPoint;
	
	public String httpVersion;
	
	@Override
	public String toString() {
		return this.verb + " " + this.endPoint;
	}

	public static HttpRequest fromString(String str) {
		if(AssertUtils.isEmpty(str)) {
			return null;
		}
		
		HttpRequest request = new HttpRequest();
		// trim leading double quotes
		str = str.trim();
		if(str.charAt(0) == '"') {
			str = str.substring(1);
		}
		
		// trim trailing double quotes
		int pos = str.length() - 1;
		if(str.charAt(pos) == '"') {
			str = str.substring(0, pos);
		}
		
		// get first space
		int firstSpace = str.indexOf(' ');
		if(firstSpace >= 0) {
			request.verb = HttpVerb.fromString(str.substring(0, firstSpace));
		} else {
			firstSpace = 0;
		}
		
		// get last space
		int lastSpace = str.lastIndexOf(' ');
		if(lastSpace >= 0) {
			request.httpVersion = str.substring(lastSpace).trim();
		} else {
			lastSpace = str.length();
		}
		
		// extract request
		request.endPoint = str.substring(firstSpace, lastSpace).trim();
		
		// done
		return request;
	}

}
