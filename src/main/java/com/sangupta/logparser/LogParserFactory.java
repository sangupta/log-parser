package com.sangupta.logparser;

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

			default:
				break;
		
		}
		
		return null;
	}
	
}
