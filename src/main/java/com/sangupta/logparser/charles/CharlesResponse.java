package com.sangupta.logparser.charles;

public class CharlesResponse {
	
	public int status;
	
	public int headersLength;
	
	public int bodyLength;

	public CharlesHeaders headers;
	
	public CharlesBody body;
	
	@Override
	public String toString() {
		return "[Status: " + this.status + ", headers: " + this.headersLength + ", body: " + this.bodyLength + "]";
	}
	
}
