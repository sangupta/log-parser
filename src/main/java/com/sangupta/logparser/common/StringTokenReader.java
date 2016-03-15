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
	
	private final int length;
	
	private int current = 0;

	public StringTokenReader(String str) {
		this.str = str;
		this.length = str.length();
	}
	
	public boolean hasNext() {
		return this.current < this.str.length();
	}
	
	public String readTillNext(char separator) {
		return this.readTillNext(String.valueOf(separator));
	}
	
	public String readTillNext(String separator) {
		if(!this.hasNext()) {
			return null;
		}
		
		int index = this.str.indexOf(separator, current);
		if(index < 0) {
			int start = this.current;
			this.current = str.length();
			return this.str.substring(start);
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

	public String readBetween(char starting, char closing) {
		if(!this.hasNext()) {
			return null;
		}
		
		int count = 0;
		int start = -1;
		boolean found = false;
		for(int index = this.current; index < this.length; index++) {
			char c = this.str.charAt(index);
			if(c == starting) {
                if(!found) {
                    start = index;
                }
                
				count++;
				found = true;
				continue;
			}
			
			if(c == closing) {
				count--;
				found = true;
				
				if(found && count == 0) {
					this.current = index + 1;
					return this.str.substring(start + 1, index);
				}
			}
		}
		
		return null;
	}

	/**
	 * Peek the first non-white-space character available
	 * 
	 * @return
	 */
	public char peekNextNonWhitespace() {
		int start = this.current;
		do {
			if(start >= this.length) {
				return 0;
			}
			
			char c = this.str.charAt(start);
			if(Character.isWhitespace(c)) {
				start++;
				continue;
			}
			
			return c;
		} while(true);
	}

}
