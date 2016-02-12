package com.sangupta.logparser.aem.request;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.common.HttpRequest;

/**
 * Represents one {@link LogLine} from <code>request.log</code> as produced
 * in AEM.
 * 
 * @author sangupta
 *
 */
public class AEMRequestLogLine extends LogLine {
	
	/**
	 * The request number
	 */
	public int requestNumber;
	
	/**
	 * If this represents request details
	 */
	public boolean inBound;
	
	/**
	 * If this represents response details
	 */
	public boolean outBound;
	
	/**
	 * The request line, if available
	 */
	public HttpRequest request;
	
	/**
	 * The status code, if available
	 */
	public int statusCode;
	
	/**
	 * The response MIME, if available
	 */
	public String mime;
	
	/**
	 * The processing time it took, if available
	 */
	public long processingTimeInMillis;

}
