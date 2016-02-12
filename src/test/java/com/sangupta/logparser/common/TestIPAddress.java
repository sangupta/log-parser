/**
 *
 * log-parser: Parsers for various log formats
 * Copyright (c) 2015-2016, Sandeep Gupta
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

package com.sangupta.logparser.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IPAddress} class.
 * 
 * @author sangupta
 *
 */
public class TestIPAddress {

	@Test
	public void test() {
		Assert.assertNull(IPAddress.fromString(null));
		Assert.assertNull(IPAddress.fromString(""));
		
		IPAddress address = IPAddress.fromString("50.112.95.211:28491");
		Assert.assertNotNull(address);
		Assert.assertEquals("50.112.95.211:28491", address.toString());
		Assert.assertEquals(846225363l, address.ip);
		Assert.assertEquals(28491, address.port);
		
		address = new IPAddress(0, 0);
		Assert.assertEquals("", address.toString());

		address = new IPAddress(846225363l, 0);
		Assert.assertEquals("50.112.95.211", address.toString());

		address = new IPAddress(846225363l, 80);
		Assert.assertEquals("50.112.95.211", address.toString());

		address = new IPAddress(846225363l, 28491);
		Assert.assertEquals("50.112.95.211:28491", address.toString());
	}
	
}
