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

/**
 * Enumeration that represents different types of parsers
 * that are available.
 * 
 * @author sangupta
 *
 */
public enum LogParsers {

	Apache_Access_Logs,
	
	Tomcat_Access_Logs,
	
	Java_Log4j,
	
	Java_LogBack,
	
	Amazon_AWS_Elastic_Load_Balancer,
	
	Adobe_Experience_Manager_Request_Logs;
	
}
