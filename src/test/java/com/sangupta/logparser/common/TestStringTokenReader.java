package com.sangupta.logparser.common;

import org.junit.Assert;
import org.junit.Test;

public class TestStringTokenReader {
    
    @Test
    public void testReadBetween() {
        StringTokenReader reader = new StringTokenReader("Hello [World]!");
        
        Assert.assertEquals("World", reader.readBetween('[', ']'));
        Assert.assertNull(reader.readBetween('(', ')'));
        
        reader = new StringTokenReader("Hello [World] - a [beautiful] place to [live] in!");
        Assert.assertEquals("World", reader.readBetween('[', ']'));
        Assert.assertEquals("beautiful", reader.readBetween('[', ']'));
        Assert.assertEquals("live", reader.readBetween('[', ']'));
    }
    
    @Test
    public void testReadTillNextChar() {
        StringTokenReader reader = new StringTokenReader("Hello [World]!");
        
        Assert.assertEquals("Hello", reader.readTillNext(' '));
        Assert.assertEquals("", reader.readTillNext('['));
        Assert.assertEquals("World", reader.readTillNext(']'));
        Assert.assertEquals("!", reader.getRemaining());
    }
    
    @Test
    public void testReadTillNextString() {
        StringTokenReader reader = new StringTokenReader("Hello [World] - a [beautiful] place to [live] in!");
        
        Assert.assertEquals("Hello [World]", reader.readTillNext(" - "));
        Assert.assertEquals("a [beautiful]", reader.readTillNext(" place"));
        Assert.assertEquals(" to [live] ", reader.readTillNext("in"));
        Assert.assertEquals("!", reader.getRemaining());
    }

}