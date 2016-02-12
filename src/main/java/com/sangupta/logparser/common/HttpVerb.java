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

package com.sangupta.logparser.common;

import com.sangupta.jerry.util.AssertUtils;

public enum HttpVerb {
	
	HEAD,
	
	GET,
	
	PUT,
	
	POST,
	
	DELETE,
	
	PATCH,
	
	OPTIONS;

	private static final HttpVerb[] verbs = HttpVerb.values();

	public static HttpVerb fromString(String str) {
		if(AssertUtils.isEmpty(str)) {
			return null;
		}
	
		str = str.toUpperCase();
		for(HttpVerb verb : verbs) {
			if(str.equals(verb.toString())) {
				return verb;
			}
		}
		
		throw new IllegalArgumentException("HttpVerb is not recognized: " + str);
	}

}
