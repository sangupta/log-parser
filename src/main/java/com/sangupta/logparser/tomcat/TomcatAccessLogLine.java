package com.sangupta.logparser.tomcat;

import com.sangupta.logparser.LogLine;
import com.sangupta.logparser.common.HttpRequest;
import com.sangupta.logparser.common.IPAddress;

public class TomcatAccessLogLine extends LogLine {
    
    public IPAddress clientIP;
    
    public HttpRequest request;
    
    public int statusCode;
    
    public long responseSize;

}
