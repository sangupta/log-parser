package com.sangupta.logparser.charles;

public class CharlesBody {
	
	public String encoding;
	
	public byte[] data;

	@Override
	public String toString() {
		return "[Body encoding: " + this.encoding + "]";
	}
	
}
