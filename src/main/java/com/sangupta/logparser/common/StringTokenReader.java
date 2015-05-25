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
