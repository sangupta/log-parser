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

package com.sangupta.logparser.aem.request;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.common.HttpRequest;

/**
 * Represents one {@link LogLine} from <code>request.log</code> as produced
 * in AEM.
 * 
 * @author sangupta
 *
 */
public class AEMRequestLogLine extends LogLine {
	
	/**
	 * The request number
	 */
	public int requestNumber;
	
	/**
	 * If this represents request details
	 */
	public boolean inBound;
	
	/**
	 * If this represents response details
	 */
	public boolean outBound;
	
	/**
	 * The request line, if available
	 */
	public HttpRequest request;
	
	/**
	 * The status code, if available
	 */
	public int statusCode;
	
	/**
	 * The response MIME, if available
	 */
	public String mime;
	
	/**
	 * The processing time it took, if available
	 */
	public long processingTimeInMillis;

}
