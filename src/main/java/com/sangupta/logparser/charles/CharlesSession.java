package com.sangupta.logparser.charles;

import java.util.ArrayList;
import java.util.List;

public class CharlesSession {
	
	public final List<CharlesTransaction> transactions = new ArrayList<>();

	@Override
	public String toString() {
		return "[Session transactions: " + this.transactions.size() + "]";
	}
	
}
