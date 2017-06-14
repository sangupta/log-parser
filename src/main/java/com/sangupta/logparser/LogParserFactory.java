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

package com.sangupta.logparser;

import com.sangupta.logparser.aem.audit.AEMAuditLogParser;
import com.sangupta.logparser.aem.error.AEMErrorLogParser;
import com.sangupta.logparser.aem.replication.AEMReplicationLogParser;
import com.sangupta.logparser.aem.request.AEMRequestLogParser;
import com.sangupta.logparser.aem.tar.AEMTarOptimizationLogParser;
import com.sangupta.logparser.elb.ElbLogParser;
import com.sangupta.logparser.tomcat.TomcatAccessLogParser;

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
			
			case Tomcat_Access_Logs:
				return new TomcatAccessLogParser();
				
			case Adobe_Experience_Manager_Request_Logs:
				return new AEMRequestLogParser();
				
			case Adobe_Experience_Manager_Audit_Logs:
			    return new AEMAuditLogParser();
			    
			case Adobe_Experience_Manager_Tar_Optimization_Logs:
			    return new AEMTarOptimizationLogParser();
			    
			case Adobe_Experience_Manager_Error_Logs:
			    return new AEMErrorLogParser();
			    
			case Adobe_Experience_Manager_Replication_Logs:
			    return new AEMReplicationLogParser();
			    
			default:
				throw new IllegalArgumentException("Unknown logfile type provided: " + parserType);
		}
	}
	
}
