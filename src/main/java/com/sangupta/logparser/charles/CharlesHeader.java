package com.sangupta.logparser.charles;

public class CharlesHeader {
	
	public String name;
	
	public String value;

	@Override
	public String toString() {
		return "[" + this.name + ": " + this.value + "]";
	}
	
}
