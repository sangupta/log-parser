/**
 *
 * log-parser: Parsers for various log formats
 * Copyright (c) 2015-2017, Sandeep Gupta
 * 
 * http://sangupta.com/projects/log-parser
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

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
