package com.sangupta.logparser.gc;

import com.sangupta.logparser.LogLine;

public class JavaGarbageCollectionLogLine extends LogLine {
	
	/**
	 * Time when the GC event started, relative to the JVM startup time. Measured in seconds.
	 */
	public double eventStartTime;
	
	/**
	 * Flag to distinguish between Minor & Full GC. This time it is indicating that this was a Minor GC.
	 */
	public GCType gcType;
	
	public double gcTime;
	
	public JavaGCMemoryRecord youngGeneration;
	
	public JavaGCMemoryRecord oldGeneration;
	
	public JavaGCMemoryRecord heap;
	
	public JavaGCMemoryRecord permanentGeneration;
	
	/**
	 * Duration of the GC event, measured in different categories:
	 */
	public JavaGCTimes times;

	// Static class follows
	
	/**
	 * Duration of the GC event, measured in different categories.
	 * 
	 * @author sangupta
	 *
	 */
	public static class JavaGCTimes {
		
		/**
		 * Total CPU time that was consumed by Garbage Collector threads during
		 * this collection.
		 */
		public double userTime;
		
		/**
		 * Time spent in OS calls or waiting for system event
		 */
		public double systemTime;
		
		/**
		 * Clock time for which your application was stopped. As Serial Garbage
		 * Collector always uses just a single thread, real time is thus equal
		 * to the sum of user and system times.
		 */
		public double realTime;
		
	}
	
	/**
	 * A record of memory for a given type - specified in KB.
	 * 
	 * @author sangupta
	 *
	 */
	public static class JavaGCMemoryRecord {
		
		public int beforeCollection;
		
		public int afterCollection;
		
		public int totalAvailable;
		
	}
	
	public static enum GCType {
		
		MinorGC,
		
		FullGC;
		
	}
}
