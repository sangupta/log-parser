package com.sangupta.logparser.aem.replication;

import java.io.BufferedReader;
import java.io.IOException;

import com.sangupta.logparser.LogParser;

public class AEMReplicationLogParser implements LogParser {

    @Override
    public String readLogLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    @Override
    public AEMReplicationLogLine parseLogLine(String logLine) {
        return null;
    }

}
