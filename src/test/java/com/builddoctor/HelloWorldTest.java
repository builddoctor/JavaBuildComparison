package com.builddoctor;

import static org.junit.Assert.*;
//import com.builddoctor.HelloWorld;

import org.junit.Test;

public class HelloWorldTest {

	@Test
	public void testHelloMessage() {
		HelloWorld helloWorld = new HelloWorld();
		assertEquals(helloWorld.helloMessage(), "Well hi there");
	}

}
