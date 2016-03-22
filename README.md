Log-Parser
----------

[![Build Status](https://travis-ci.org/sangupta/log-parser.svg?branch=master)](https://travis-ci.org/sangupta/log-parser)
[![Coverage Status](https://coveralls.io/repos/github/sangupta/log-parser/badge.svg?branch=master)](https://coveralls.io/github/sangupta/log-parser?branch=master)
[![Maven Version](https://maven-badges.herokuapp.com/maven-central/com.sangupta/log-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.sangupta/log-parser)

A simple Java libary to parse various kind of known log files - parsing of the following
formats is currently supported:

* Amazon AWS ELB logs
* Adobe Experience Manager Audit logs
* Adobe Experience Manager Error logs
* Adobe Experience Manager Request logs
* Adobe Experience Manager Replication logs
* Adobe Experience Manager Tar Optimization logs
* Sun/Oracle JDK GC logs
* Tomcat access logs (default format)

### RoadMap

The following log formats shall be added to the library next:

* log4j
* logback
* Apache httpd access logs

### Building

Building the library is very simple.

```shell
$ git clone git@github.com:sangupta/log-parser.git
$ cd log-parser
$ mvn clean package
```

### Downloading

You may include the library in your Maven project by adding the following to the `pom.xml`

```xml
  <repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
	<dependency>
	    <groupId>com.github.sangupta</groupId>
	    <artifactId>log-parser</artifactId>
	    <version>-SNAPSHOT</version>
	</dependency>
```

License
-------

```
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
```
