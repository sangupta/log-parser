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

package com.sangupta.logparser;

import com.sangupta.logparser.aem.audit.AEMAuditLogParser;
import com.sangupta.logparser.aem.request.AEMRequestLogParser;
import com.sangupta.logparser.elb.ElbLogParser;

/**
 * Factory class to return the right parser as needed.
 * 
 * @author sangupta
 *
 */
public class LogParserFactory {

	public static LogParser getParser(LogParsers parserType) {
		if(parserType == null) {
			throw new IllegalArgumentException("ParserType cannot be null");
		}
		
		switch(parserType) {
			case Amazon_AWS_Elastic_Load_Balancer:
				return new ElbLogParser();
			
			case Apache_Access_Logs:
				break;
			
			case Java_Log4j:
				break;
			
			case Java_LogBack:
				break;
			
			case Tomcat_Access_Logs:
				break;
				
			case Adobe_Experience_Manager_Request_Logs:
				return new AEMRequestLogParser();
				
			case Adobe_Experience_Manager_Audit_Logs:
			    return new AEMAuditLogParser();

			default:
				break;
		
		}
		
		return null;
	}
	
}
