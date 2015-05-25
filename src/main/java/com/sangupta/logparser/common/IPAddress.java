package com.sangupta.logparser.common;

import com.sangupta.jerry.util.AssertUtils;

public class IPAddress {
	
	public long ip;
	
	public int port;
	
	/**
	 * Default constructor
	 * 
	 */
	public IPAddress() {
		
	}
	
	/**
	 * Convenience constructor
	 * 
	 * @param ip
	 * @param port
	 */
	public IPAddress(long ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public String toString() {
		if(this.ip == 0 && this.port == 0) {
			return "";
		}
		
		String ipAddress = "" + ((this.ip >>> 24) & 0xff)
						+ "." + ((this.ip >>> 16) & 0xff)
						+ "." + ((this.ip >>> 8) & 0xff)
						+ "." + ((this.ip) & 0xff);
		
		if(this.port == 0 || this.port == 80) {
			return ipAddress;
		}
		
		return ipAddress + ":" + this.port;
	}
	
	@Override
	public int hashCode() {
		if(this.ip == 0) {
			return 0;
		}
		
		return (int)(this.ip ^ (this.ip >>> 32));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof IPAddress)) {
			return false;
		}
		
		IPAddress other = (IPAddress) obj;
		return this.ip == other.ip && this.port == other.port;
	}

	/**
	 * Convenience method to parse a given string into an {@link IPAddress}
	 * instance.
	 * 
	 * @param str
	 * @return
	 */
	public static IPAddress fromString(String str) {
		if(AssertUtils.isEmpty(str)) {
			return null;
		}

		int port = 0;
		
		int portIndex = str.indexOf(':');
		if(portIndex >= 0) {
			// parse port
			String p = str.substring(portIndex + 1);
			port = Integer.parseInt(p);
			str = str.substring(0, portIndex);
		}
		
		// now parse the IP
		int[] ip = new int[4];
		String[] parts = str.split("\\.");

		for (int i = 0; i < 4; i++) {
		    ip[i] = Integer.parseInt(parts[i]);
		}
		
		long ipNumbers = 0;
		for (int i = 0; i < 4; i++) {
		    ipNumbers += ip[i] << (24 - (8 * i));
		}
		
		return new IPAddress(ipNumbers, port);
	}

}
