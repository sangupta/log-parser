package com.sangupta.logparser.aem.audit;

import com.sangupta.logparser.LogLine;

public class AEMAuditLogLine extends LogLine {

    public int eventID;
    
    public long journalRevisionID;
    
    public String user;
    
    public String workspace;
    
    public String nodePath;
    
    public int sizeOfUpdate;
    
}
