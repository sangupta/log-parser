package com.sangupta.logparser.charles;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestCharlesSessionXML {

	public void testCharlesSessionXML() throws Exception {
		String file = FileUtils.readFileToString(new File("/Users/sangupta/Desktop/charles-files/charles-session.xml"));
		CharlesSession session = CharlesSessionXmlLogParser.parse(file);
		System.out.println(session);
	}
	
}
