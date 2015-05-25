package com.sangupta.logparser;

public interface LogParser {
	
	/**
	 * Read a log line from the source - a complete line may span
	 * many lines for example, in java logs stack traces are also
	 * a part of the line
	 * 
	 */
	public String readLogLine();

	/**
	 * Parse the extracted log line and return the {@link LogLine}
	 * that represents a strongly typed structure.
	 * 
	 * @param logLine
	 * @return
	 */
	public LogLine parseLogLine(String logLine);
}
