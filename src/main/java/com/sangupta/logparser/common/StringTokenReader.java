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

/**
 * A class that allows reading a {@link String} via simple tokens.
 * 
 * @author sangupta
 *
 */
public class StringTokenReader {
	
	private final String str;
	
	private int current = 0;

	public StringTokenReader(String str) {
		this.str = str;
	}
	
	public boolean hasNext() {
		return this.current < this.str.length();
	}
	
	public String readTillNext(char separator) {
		return this.readTillNext(String.valueOf(separator));
	}
	
	public String readTillNext(String separator) {
		if(!this.hasNext()) {
			throw new IllegalStateException("No more tokens are available");
		}
		
		int index = this.str.indexOf(separator, current);
		if(index < 0) {
			this.current = str.length();
			return this.str.substring(this.current);
		}
		
		String extracted = this.str.substring(this.current, index);
		this.current = index + separator.length();
		return extracted;
	}

	public String getRemaining() {
		if(!this.hasNext()) {
			throw new IllegalStateException("No more tokens are available");
		}
		
		return this.str.substring(this.current);
	}
}
