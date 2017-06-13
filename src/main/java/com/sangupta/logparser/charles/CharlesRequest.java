package com.sangupta.logparser.charles;

public class CharlesRequest {
	
	public int headersLength;
	
	public int bodyLength;
	
	public CharlesHeaders headers;
	
	public CharlesBody body;
	
	@Override
	public String toString() {
		return "[Headers: " + this.headersLength + ", body: " + this.bodyLength + "]";
	}

}
