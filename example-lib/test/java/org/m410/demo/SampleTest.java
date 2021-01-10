package org.m410.demo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SampleTest {
    @Test public void testHello() {
       Sample sample = new Sample();
       assertEquals("Hello",sample.sayHello());
    }

}
