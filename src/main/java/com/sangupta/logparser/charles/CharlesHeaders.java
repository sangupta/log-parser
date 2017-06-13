package com.sangupta.logparser.charles;

import java.util.ArrayList;
import java.util.List;

public class CharlesHeaders {
	
	public String firstLine;
	
	public final List<CharlesHeader> list = new ArrayList<>();

	@Override
	public String toString() {
		return "[" + this.firstLine + ", headers: " + this.list.size() + "]";
	}
	
}
