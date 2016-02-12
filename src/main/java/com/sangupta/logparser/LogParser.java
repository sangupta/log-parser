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

public interface LogParser {
	
	/**
	 * Read a log line from the source - a complete line may span
	 * many lines for example, in java logs stack traces are also
	 * a part of the line
	 * 
	 */
	public String readLogLine();

	/**
	 * Parse the extracted log line and return the {@link LogLine}
	 * that represents a strongly typed structure.
	 * 
	 * @param logLine
	 * @return
	 */
	public LogLine parseLogLine(String logLine);
}
