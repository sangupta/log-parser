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

package com.sangupta.logparser.gc;

import org.junit.Assert;
import org.junit.Test;

import com.sangupta.logparser.gc.JavaGarbageCollectionLogLine.GCType;
import com.sangupta.logparser.gc.JavaGarbageCollectionLogLine.JavaGCMemoryRecord;

public class TestJavaGarbageCollectionLogParser {
	
	@Test
	public void testMemoryRecordParsing() {
		String str = "10198317K->7911921K(18641920K)";
		JavaGCMemoryRecord record = JavaGarbageCollectionLogParser.parseMemoryRecord(str);
		Assert.assertNotNull(record);
		Assert.assertEquals(record.beforeCollection, 10198317);
		Assert.assertEquals(record.afterCollection, 7911921);
		Assert.assertEquals(record.totalAvailable, 18641920);
	}

	@Test
	public void testParsing() {
		String text = "2016-01-22T11:55:02.722+0530: 138.050: [GC [PSYoungGen: 2459504K->131416K(4660736K)] 10198317K->7911921K(18641920K), 0.1234780 secs] [Times: user=0.69 sys=0.06, real=0.12 secs]";
		JavaGarbageCollectionLogParser parser = new JavaGarbageCollectionLogParser();
		JavaGarbageCollectionLogLine line = parser.parseLogLine(text);
		
		Assert.assertNotNull(line);
		Assert.assertEquals(138.05d, line.eventStartTime, 0d);
		Assert.assertEquals(GCType.MinorGC, line.gcType);
		
		Assert.assertEquals(1453443902722l, line.timestamp);
		Assert.assertEquals(0.123478d, line.gcTime, 0d);
		
		Assert.assertNotNull(line.youngGeneration);
		Assert.assertNotNull(line.heap);
		Assert.assertNotNull(line.times);
		
		Assert.assertNull(line.permanentGeneration);
		Assert.assertNull(line.oldGeneration);
		
		Assert.assertEquals(2459504, line.youngGeneration.beforeCollection);
		Assert.assertEquals(131416, line.youngGeneration.afterCollection);
		Assert.assertEquals(4660736, line.youngGeneration.totalAvailable);
		
		Assert.assertEquals(10198317, line.heap.beforeCollection);
		Assert.assertEquals(7911921, line.heap.afterCollection);
		Assert.assertEquals(18641920, line.heap.totalAvailable);

		Assert.assertEquals(0.69d, line.times.userTime, 0d);
		Assert.assertEquals(0.06d, line.times.systemTime, 0d);
		Assert.assertEquals(0.12d, line.times.realTime, 0d);
	}
	
	@Test
	public void testParsing2() {
		String text = "2016-01-22T11:52:48.791+0530: 4.119: [Full GC [PSYoungGen: 32896K->0K(6117376K)] [ParOldGen: 8K->31881K(13981184K)] 32904K->31881K(20098560K) [PSPermGen: 12871K->12864K(26112K)], 0.1390330 secs] [Times: user=0.31 sys=0.04, real=0.14 secs] ";
		JavaGarbageCollectionLogParser parser = new JavaGarbageCollectionLogParser();
		JavaGarbageCollectionLogLine line = parser.parseLogLine(text);
		
		Assert.assertNotNull(line);
		Assert.assertEquals(4.119d, line.eventStartTime, 0d);
		Assert.assertEquals(GCType.FullGC, line.gcType);
		
		Assert.assertEquals(1453443768791l, line.timestamp);
		Assert.assertEquals(0.1390330d, line.gcTime, 0d);
		
		Assert.assertNotNull(line.youngGeneration);
		Assert.assertNotNull(line.oldGeneration);
		Assert.assertNotNull(line.heap);
		Assert.assertNotNull(line.permanentGeneration);
		Assert.assertNotNull(line.times);
		
		
		Assert.assertEquals(32896, line.youngGeneration.beforeCollection);
		Assert.assertEquals(0, line.youngGeneration.afterCollection);
		Assert.assertEquals(6117376, line.youngGeneration.totalAvailable);
		
		Assert.assertEquals(8, line.oldGeneration.beforeCollection);
		Assert.assertEquals(31881, line.oldGeneration.afterCollection);
		Assert.assertEquals(13981184, line.oldGeneration.totalAvailable);
		
		Assert.assertEquals(12871, line.permanentGeneration.beforeCollection);
		Assert.assertEquals(12864, line.permanentGeneration.afterCollection);
		Assert.assertEquals(26112, line.permanentGeneration.totalAvailable);
		
		Assert.assertEquals(32904, line.heap.beforeCollection);
		Assert.assertEquals(31881, line.heap.afterCollection);
		Assert.assertEquals(20098560, line.heap.totalAvailable);

		Assert.assertEquals(0.31d, line.times.userTime, 0d);
		Assert.assertEquals(0.04d, line.times.systemTime, 0d);
		Assert.assertEquals(0.14d, line.times.realTime, 0d);
	}
}
